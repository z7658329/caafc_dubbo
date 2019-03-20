package com.micro.util;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URISyntaxException;
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
            // 设置协议http和https对应的处理socket链接工厂的对象
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslcontext))
                    .build();
            cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            cm.setMaxTotal(1000);
            cm.setDefaultMaxPerRoute(100);
            HttpClientBuilder clientBuilder = HttpClients.custom();
            //长连接
            clientBuilder.setKeepAliveStrategy((httpResponse, httpContext) -> {
                BasicHeaderElementIterator it = new BasicHeaderElementIterator(httpResponse.headerIterator("Keep-Alive"));
                while(true) {
                    String param;
                    String value;
                    do {
                        do {
                            if (!it.hasNext()) {
                                return -1L;
                            }
                            HeaderElement he = it.nextElement();
                            param = he.getName();
                            value = he.getValue();
                        } while(value == null);
                    } while(!param.equalsIgnoreCase("timeout"));

                    try {
                        return Long.parseLong(value) * 1000L;
                    } catch (NumberFormatException e) {
                        log.error(LG.E(),e);
                    }
                }
            });
            client = clientBuilder.setConnectionManager(cm).build();
        } catch (NoSuchAlgorithmException e) {
            log.error(LG.E(),e);
        } catch (KeyManagementException e) {
            log.error(LG.E(),e);
        } catch (NoSuchProviderException e) {
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
                    get.setHeader(entry.getKey(), entry.getValue() + "");
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
            StringEntity entity = new StringEntity(content, UTF8);
            post.setHeader("Content-Type", "application/json;charset=utf-8");
            post.setHeader("Accept", "application/json");
            post.setEntity(entity);
            if (header != null) {
                for (Map.Entry<String, Object> entry : header.entrySet()) {
                    post.setHeader(entry.getKey(), entry.getValue() + "");
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
            HttpEntity entity = response.getEntity();
            entityStr = EntityUtils.toString(entity, UTF8);
        } catch (Exception e) {
            log.error(LG.E(),e);
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
