package com.micro.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.InterruptedIOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.Map;

/**
 * HTTP工具类
 */
@Slf4j
public class HttpUtils {

    private final static String UTF8 = "UTF-8";
    private static PoolingHttpClientConnectionManager cm;
    private static CloseableHttpClient client;

    /**
     * 私有化构造器
     */
    static {
        try {
            SSLContext sslcontext = createIgnoreVerifySSL();
            // 设置连接池 协议http和https对应的处理socket链接工厂的对象
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslcontext))
                    .build();
            cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            cm.setMaxTotal(1000);
            cm.setDefaultMaxPerRoute(100);

            //超时设置
            RequestConfig requestConfig=RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(1000).setConnectionRequestTimeout(1000).build();

            //长连接
            ConnectionKeepAliveStrategy myStrategy = (response, context) -> {
                HeaderElementIterator it = new BasicHeaderElementIterator
                        (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase
                            ("timeout")) {
                        return Long.parseLong(value) * 1000;
                    }
                }
                return 60 * 1000;//如果没有约定，则默认定义时长为60s
            };
            //请求失败时,进行请求重试
            HttpRequestRetryHandler handler = (e, i, httpContext) -> {
                if (i > 3){
                    //重试超过3次,放弃请求
                    log.error("retry has more than 3 time, give up request");
                    return false;
                }
                if (e instanceof NoHttpResponseException){
                    //服务器没有响应,可能是服务器断开了连接,应该重试
                    log.error("receive no response from server, retry");
                    return true;
                }
                if (e instanceof SSLHandshakeException){
                    // SSL握手异常
                    log.error("SSL hand shake exception");
                    return false;
                }
                if (e instanceof InterruptedIOException){
                    //超时
                    log.error("InterruptedIOException");
                    return false;
                }
                if (e instanceof UnknownHostException){
                    // 服务器不可达
                    log.error("server host unknown");
                    return false;
                }
                if (e instanceof ConnectTimeoutException){
                    // 连接超时
                    log.error("Connection Time out");
                    return false;
                }
                if (e instanceof SSLException){
                    log.error("SSLException");
                    return false;
                }

                HttpClientContext context = HttpClientContext.adapt(httpContext);
                HttpRequest request = context.getRequest();
                if (!(request instanceof HttpEntityEnclosingRequest)){
                    //如果请求不是关闭连接的请求
                    return true;
                }
                return false;
            };
            client =  HttpClients.custom().setConnectionManager(cm).setKeepAliveStrategy(myStrategy).setDefaultRequestConfig(requestConfig).setRetryHandler(handler).build();
        } catch (Exception e) {
            log.error(LG.E(),e);
        }
    }


    public static String get(String url) {
        String respStr = null;
        HttpGet get = null;
        try {
            get = new HttpGet(url);
            respStr = execute(get);
        } catch (Exception e) {
            log.error(LG.E(),e);
        } finally {
            get.releaseConnection();
        }
        return respStr;
    }


    public static String getWithParamAndHeader(String url, Map<String, Object> urlParam, Map<String, Object> header) {
        String respStr = null;
        HttpGet get = null;
        try {
            url = getParamUrl(url, urlParam);
            get = new HttpGet(url);
            if (null != header) {
                for (Map.Entry<String, Object> entry : header.entrySet()) {
                    get.setHeader(entry.getKey(), entry.getValue().toString());
                }
            }
            respStr = execute(get);
        } catch (Exception e) {
            log.error(LG.E(),e);
        } finally {
            get.releaseConnection();
        }
        return respStr;
    }


    public static String post(String url, String content) {
        String respStr = null;
        HttpPost post = null;
        try {
            post = new HttpPost(url);
            StringEntity entity = new StringEntity(content, UTF8);
            post.setHeader("Content-Type", "application/json;charset=utf-8");
            post.setHeader("Accept", "application/json");
            post.setEntity(entity);
            respStr = execute(post);
            post.releaseConnection();
        } catch (Exception e) {
            log.error(LG.E(),e);
        } finally {
            post.releaseConnection();
        }
        return respStr;
    }

    public static String doPostWithParamAndHeader(String url, String content, Map<String, Object> urlParam, Map<String, Object> header) {
        String respStr = null;
        HttpPost post = null;
        try {
            url = getParamUrl(url, urlParam);
            post = new HttpPost(url);
            StringEntity entity;
            if(content!=null){
                entity = new StringEntity(content, UTF8);
                post.setEntity(entity);
            }
            post.addHeader("Content-Type", "application/json;charset=utf-8");
            post.addHeader("Accept", "application/json");
            if (header != null) {
                for (Map.Entry<String, Object> entry : header.entrySet()) {
                    post.addHeader(entry.getKey(), entry.getValue().toString());
                }
            }
            respStr = execute(post);
            post.releaseConnection();
        } catch (Exception e) {
            log.error(LG.E(),e);
        } finally {
            post.releaseConnection();
        }
        return respStr;
    }

    private static String getParamUrl(String url, Map<String, Object> urlParam) {
        if (null != urlParam) {
            url = url + "?";
            for (Map.Entry<String, Object> entry : urlParam.entrySet()) {

                url = url + entry.getKey() + "=";
                url = url + entry.getValue() + "&";
            }
            url = url.substring(0, url.length() - 1);
        }

        return url;
    }

    private static String getParamUrl2(String url, Map<String, Object> urlParam) {
        if (null != urlParam) {
            URIBuilder uriBuilder=null;
            try {
                uriBuilder = new URIBuilder(url);
            } catch (URISyntaxException e) {
                log.error(LG.E(),e);
            }
            for (Map.Entry<String, Object> entry : urlParam.entrySet()) {
                uriBuilder.setParameter( entry.getKey(),entry.getValue().toString());
            }

        }
        return url;
    }

    private static String execute(HttpUriRequest uriRequest) {
        CloseableHttpResponse response ;
        String entityStr = null;
        try {
            response = client.execute(uriRequest);
            //if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
            HttpEntity entity = response.getEntity();
            entityStr = EntityUtils.toString(entity, UTF8);
        } catch (Exception e) {
            return e.getMessage();
        }
        return entityStr;
    }

    /**
     * 绕过验证
     */
    private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException, NoSuchProviderException {
        //SSLContext sc = SSLContext.getInstance("SSLv3");
        SSLContext sc = SSLContext.getInstance("SSL", "SunJSSE");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }
}
