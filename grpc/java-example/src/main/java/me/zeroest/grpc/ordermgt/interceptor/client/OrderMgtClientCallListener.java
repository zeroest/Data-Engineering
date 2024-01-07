package me.zeroest.grpc.ordermgt.interceptor.client;

import io.grpc.ClientCall;
import io.grpc.ForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.Status;

import java.util.logging.Logger;

public class OrderMgtClientCallListener<R> extends ForwardingClientCallListener<R> {

    private static final Logger logger = Logger.getLogger(OrderMgtClientCallListener.class.getName());

    private final ClientCall.Listener<R> delegate;

    private R message;

    public OrderMgtClientCallListener(ClientCall.Listener<R> delegate) {
        this.delegate = delegate;
    }

    @Override
    protected ClientCall.Listener<R> delegate() {
        return delegate;
    }

    @Override
    public void onReady() {
        logger.info("onReady : " + message);
        super.onReady();
    }

    @Override
    public void onHeaders(Metadata headers) {
        logger.info("onHeaders : " + headers);
        super.onHeaders(headers);
    }

    @Override
    public void onMessage(R message) {
        logger.info("onMessage");
        logger.info("Message Received from Client -> Service " + message);
        this.message = message;
        super.onMessage(message);
    }

    @Override
    public void onClose(Status status, Metadata trailers) {
        logger.info("onClose - status: '" + status + "' trailers: '" + trailers + "'");
        super.onClose(status, trailers);
    }

}
