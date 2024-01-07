package me.zeroest.grpc.ordermgt.interceptor.client;

import io.grpc.*;
import me.zeroest.grpc.ordermgt.interceptor.server.OrderMgtServerCallListener;

import java.util.logging.Logger;

public class OrderMgtClientInterceptor implements ClientInterceptor {

    private static final Logger logger = Logger.getLogger(OrderMgtServerCallListener.class.getName());

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        logger.info("=== Client interceptor Before");
        logger.info("method: '" + method + "' callOptions: '" + callOptions + "' channel: '" + next + "'");
        ClientCall<ReqT, RespT> call = next.newCall(method, callOptions);
        return new OrderMgtClientCall<>(call);
    }

    // method: 'MethodDescriptor{fullMethodName=me.zeroest.grpc.ecommerce.v1.OrderManagement/getOrder, type=UNARY, idempotent=false, safe=false, sampledToLocalTracing=true, requestMarshaller=io.grpc.protobuf.lite.ProtoLiteUtils$MessageMarshaller@69997e9d, responseMarshaller=io.grpc.protobuf.lite.ProtoLiteUtils$MessageMarshaller@793be5ca, schemaDescriptor=me.zeroest.grpc.ecommerce.v1.OrderManagementGrpc$OrderManagementMethodDescriptorSupplier@2df9b86}'
    // callOptions: 'CallOptions{deadline=null, authority=null, callCredentials=null, executor=class io.grpc.stub.ClientCalls$ThreadlessExecutor, compressorName=null, customOptions=[[internal-stub-type, BLOCKING]], waitForReady=false, maxInboundMessageSize=null, maxOutboundMessageSize=null, streamTracerFactories=[]}'

}
