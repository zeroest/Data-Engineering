package me.zeroest.grpc.ordermgt.interceptor.client;

import io.grpc.ClientCall;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;

import javax.annotation.Nullable;
import java.util.logging.Logger;

public class OrderMgtClientCall<ReqT, RespT> extends ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT> {

    private static final Logger logger = Logger.getLogger(OrderMgtClientCall.class.getName());

    OrderMgtClientCall(ClientCall<ReqT, RespT> delegate) {
        super(delegate);
    }

    @Override
    public void start(Listener<RespT> responseListener, Metadata headers) {
        logger.info("start - listener: '" + responseListener + "' headers: '" + headers + "'");
        OrderMgtClientCallListener<RespT> clientCallListener = new OrderMgtClientCallListener<>(responseListener);
        super.start(clientCallListener, headers);
    }

    @Override
    public void request(int numMessages) {
        logger.info("request - numMessage: '" + numMessages + "'");
        super.request(numMessages);
    }

    @Override
    public void sendMessage(ReqT message) {
        logger.info("=== sendMessage | message : " + message);
        super.sendMessage(message);
    }

    @Override
    public void halfClose() {
        logger.info("halfClose");
        super.halfClose();
    }

    @Override
    public void cancel(@Nullable String message, @Nullable Throwable cause) {
        logger.info("cancel - message: '" + message + "' cause: '" + cause + "'");
        super.cancel(message, cause);
    }

}
