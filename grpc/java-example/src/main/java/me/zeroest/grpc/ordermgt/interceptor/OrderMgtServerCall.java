package me.zeroest.grpc.ordermgt.interceptor;

import io.grpc.*;

import java.util.logging.Logger;

public class OrderMgtServerCall<ReqT, RespT> extends ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>  {

    private static final Logger logger = Logger.getLogger(OrderMgtServerCall.class.getName());

    OrderMgtServerCall(ServerCall<ReqT, RespT> delegate) {
        super(delegate);
    }

    @Override
    protected ServerCall<ReqT, RespT> delegate() {
        return super.delegate();
    }

    @Override
    public MethodDescriptor<ReqT, RespT> getMethodDescriptor() {
        return super.getMethodDescriptor();
    }

    @Override
    public void request(int numMessages) {
        logger.info("=== request | numMessage : " + numMessages);
        super.request(numMessages);
    }

    @Override
    public void sendHeaders(Metadata headers) {
        logger.info("=== sendHeaders | headers : " + headers);
        super.sendHeaders(headers);
    }

    @Override
    public void sendMessage(RespT message) {
        logger.info("=== sendMessage");
        logger.info("Message from Service -> Client : " + message);
        super.sendMessage(message);
    }

    @Override
    public void close(Status status, Metadata trailers) {
        logger.info("=== close | status : " + status + ", trailers : " + trailers);
        super.close(status, trailers);
    }

    // ==============================================================================

    @Override
    public boolean isReady() {
        logger.info("=== isReady ");
        return super.isReady();
    }

    @Override
    public boolean isCancelled() {
        logger.info("=== isCancelled ");
        return super.isCancelled();
    }

    @Override
    public void setMessageCompression(boolean enabled) {
        logger.info("=== setMessageCompression | enabled : " + enabled);
        super.setMessageCompression(enabled);
    }

    @Override
    public void setCompression(String compressor) {
        logger.info("=== setCompression | compressor : " + compressor);
        super.setCompression(compressor);
    }

    @Override
    public Attributes getAttributes() {
        logger.info("=== getAttributes ");
        return super.getAttributes();
    }

    @Override
    public String getAuthority() {
        logger.info("=== getAuthority ");
        return super.getAuthority();
    }

}
