package me.zeroest.grpc.ordermgt.interceptor;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

import java.util.logging.Logger;

public class OrderMgtServerInterceptor implements ServerInterceptor {
    private static final Logger logger = Logger.getLogger(OrderMgtServerInterceptor.class.getName());

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        logger.info("======= [Server Interceptor] : Remote Method Invoked - " + call.getMethodDescriptor().getFullMethodName());
        ServerCall<ReqT, RespT> serverCall = new OrderMgtServerCall<>(call);
        ServerCall.Listener<ReqT> serverCallListener = next.startCall(serverCall, headers);
        return new OrderMgtServerCallListener<>(serverCallListener);
    }

}
