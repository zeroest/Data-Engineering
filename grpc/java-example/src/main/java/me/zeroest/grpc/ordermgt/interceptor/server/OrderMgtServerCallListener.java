package me.zeroest.grpc.ordermgt.interceptor.server;

import io.grpc.ForwardingServerCallListener;
import io.grpc.ServerCall;

import java.util.logging.Logger;

public class OrderMgtServerCallListener<R> extends ForwardingServerCallListener<R> {

    private static final Logger logger = Logger.getLogger(OrderMgtServerCallListener.class.getName());

    private final ServerCall.Listener<R> delegate;

    private R message;

    OrderMgtServerCallListener(ServerCall.Listener<R> delegate) {
        this.delegate = delegate;
    }

    @Override
    protected ServerCall.Listener<R> delegate() {
        return delegate;
    }

    @Override
    public void onReady() {
        logger.info("onReady : " + message);
        super.onReady();
    }

    @Override
    public void onMessage(R message) {
        logger.info("onMessage");
        logger.info("Message Received from Client -> Service " + message);
        this.message = message;
        super.onMessage(message);
    }

    @Override
    public void onHalfClose() {
        logger.info("onHalfClose : " + message);
        super.onHalfClose();
    }

    @Override
    public void onComplete() {
        logger.info("onComplete : " + message);
        super.onComplete();
    }

    @Override
    public void onCancel() {
        logger.info("onCancel : " + message);
        super.onCancel();
    }

}
