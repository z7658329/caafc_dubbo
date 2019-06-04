package com.micro.netty;

import com.alibaba.fastjson.JSONObject;
import com.micro.model.BaseResponse;
import com.micro.model.HttpRequest;
import com.micro.util.SpringUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.stream.Collectors;

@ChannelHandler.Sharable
@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String allowUrl="/collect";
    private final String length="/length";


    private DispatcherService dispatcherService= SpringUtils.getBean(DispatcherService.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
//        String ip = insocket.getAddress().getHostAddress();
//        log.info("{} 已经获取到客户端连接......",ip);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        try{
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
            String path=queryStringDecoder.path();
            Map<String,String>parameters=queryStringDecoder.parameters().entrySet().stream().collect(Collectors.toMap(e->e.getKey(),e->e.getValue().get(0)));
            Map<String,String>headers=request.headers().entries().stream().collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));
            String content=request.content().toString(Charset.forName("UTF-8"));
            String method=request.method().name();
            HttpRequest httpRequest=new HttpRequest(path,method,parameters,headers,content);
            Object result=doDispatch(httpRequest);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(JSONObject.toJSONString(result) , CharsetUtil.UTF_8));
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json;charset=UTF-8");
            response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,response.content().readableBytes());
            response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN,"*");
            response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS,"*");//允许headers自定义
            response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS,"GET, POST, PUT,DELETE");
            response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS,"true");
            ChannelFuture channelFuture=ctx.writeAndFlush(response);
//            if (!HttpUtil.isKeepAlive(request)) {
//                channelFuture.addListener((ChannelFutureListener.CLOSE));
//            }
        }catch (Exception e){
            log.info("channelRead Exception:{}",e.toString());
        }
    }

    private Object doDispatch(HttpRequest httpRequest) throws Exception {
        switch (httpRequest.getPath()){
            case allowUrl:
                doAllowUrl(httpRequest);
            case length:
                return BaseResponse.create(dispatcherService.getQueue().size());
            default:
                return BaseResponse.createError("url error");
        }

    }

    private BaseResponse doAllowUrl(HttpRequest httpRequest){
        if(httpRequest.getContent()==null||httpRequest.getContent().length()==0){
            return BaseResponse.createError("content null");
        }
        boolean ret=dispatcherService.getQueue().offer(httpRequest);
        if(ret){
            return BaseResponse.create(null);
        }else {
            return BaseResponse.createError("插入队列异常，可能队列已满");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}