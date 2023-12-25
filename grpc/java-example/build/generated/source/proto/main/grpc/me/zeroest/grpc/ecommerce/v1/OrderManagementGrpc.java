package me.zeroest.grpc.ecommerce.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.39.0)",
    comments = "Source: order_management.proto")
public final class OrderManagementGrpc {

  private OrderManagementGrpc() {}

  public static final String SERVICE_NAME = "me.zeroest.grpc.ecommerce.v1.OrderManagement";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.StringValue,
      me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order> getGetOrderMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getOrder",
      requestType = com.google.protobuf.StringValue.class,
      responseType = me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.StringValue,
      me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order> getGetOrderMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.StringValue, me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order> getGetOrderMethod;
    if ((getGetOrderMethod = OrderManagementGrpc.getGetOrderMethod) == null) {
      synchronized (OrderManagementGrpc.class) {
        if ((getGetOrderMethod = OrderManagementGrpc.getGetOrderMethod) == null) {
          OrderManagementGrpc.getGetOrderMethod = getGetOrderMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.StringValue, me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getOrder"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.StringValue.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order.getDefaultInstance()))
              .setSchemaDescriptor(new OrderManagementMethodDescriptorSupplier("getOrder"))
              .build();
        }
      }
    }
    return getGetOrderMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.StringValue,
      me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order> getSearchOrdersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "searchOrders",
      requestType = com.google.protobuf.StringValue.class,
      responseType = me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.google.protobuf.StringValue,
      me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order> getSearchOrdersMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.StringValue, me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order> getSearchOrdersMethod;
    if ((getSearchOrdersMethod = OrderManagementGrpc.getSearchOrdersMethod) == null) {
      synchronized (OrderManagementGrpc.class) {
        if ((getSearchOrdersMethod = OrderManagementGrpc.getSearchOrdersMethod) == null) {
          OrderManagementGrpc.getSearchOrdersMethod = getSearchOrdersMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.StringValue, me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "searchOrders"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.StringValue.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order.getDefaultInstance()))
              .setSchemaDescriptor(new OrderManagementMethodDescriptorSupplier("searchOrders"))
              .build();
        }
      }
    }
    return getSearchOrdersMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static OrderManagementStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OrderManagementStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OrderManagementStub>() {
        @java.lang.Override
        public OrderManagementStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OrderManagementStub(channel, callOptions);
        }
      };
    return OrderManagementStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static OrderManagementBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OrderManagementBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OrderManagementBlockingStub>() {
        @java.lang.Override
        public OrderManagementBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OrderManagementBlockingStub(channel, callOptions);
        }
      };
    return OrderManagementBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static OrderManagementFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OrderManagementFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OrderManagementFutureStub>() {
        @java.lang.Override
        public OrderManagementFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OrderManagementFutureStub(channel, callOptions);
        }
      };
    return OrderManagementFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class OrderManagementImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 주문을 조회하기 위한 원격 메서드
     * </pre>
     */
    public void getOrder(com.google.protobuf.StringValue request,
        io.grpc.stub.StreamObserver<me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetOrderMethod(), responseObserver);
    }

    /**
     * <pre>
     * Order의 stream메세지를 반환해 서버 스트리밍을 정의한다
     * </pre>
     */
    public void searchOrders(com.google.protobuf.StringValue request,
        io.grpc.stub.StreamObserver<me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSearchOrdersMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetOrderMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.StringValue,
                me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order>(
                  this, METHODID_GET_ORDER)))
          .addMethod(
            getSearchOrdersMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                com.google.protobuf.StringValue,
                me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order>(
                  this, METHODID_SEARCH_ORDERS)))
          .build();
    }
  }

  /**
   */
  public static final class OrderManagementStub extends io.grpc.stub.AbstractAsyncStub<OrderManagementStub> {
    private OrderManagementStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OrderManagementStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OrderManagementStub(channel, callOptions);
    }

    /**
     * <pre>
     * 주문을 조회하기 위한 원격 메서드
     * </pre>
     */
    public void getOrder(com.google.protobuf.StringValue request,
        io.grpc.stub.StreamObserver<me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOrderMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Order의 stream메세지를 반환해 서버 스트리밍을 정의한다
     * </pre>
     */
    public void searchOrders(com.google.protobuf.StringValue request,
        io.grpc.stub.StreamObserver<me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getSearchOrdersMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class OrderManagementBlockingStub extends io.grpc.stub.AbstractBlockingStub<OrderManagementBlockingStub> {
    private OrderManagementBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OrderManagementBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OrderManagementBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 주문을 조회하기 위한 원격 메서드
     * </pre>
     */
    public me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order getOrder(com.google.protobuf.StringValue request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOrderMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Order의 stream메세지를 반환해 서버 스트리밍을 정의한다
     * </pre>
     */
    public java.util.Iterator<me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order> searchOrders(
        com.google.protobuf.StringValue request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getSearchOrdersMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class OrderManagementFutureStub extends io.grpc.stub.AbstractFutureStub<OrderManagementFutureStub> {
    private OrderManagementFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OrderManagementFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OrderManagementFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 주문을 조회하기 위한 원격 메서드
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order> getOrder(
        com.google.protobuf.StringValue request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOrderMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ORDER = 0;
  private static final int METHODID_SEARCH_ORDERS = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final OrderManagementImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(OrderManagementImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ORDER:
          serviceImpl.getOrder((com.google.protobuf.StringValue) request,
              (io.grpc.stub.StreamObserver<me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order>) responseObserver);
          break;
        case METHODID_SEARCH_ORDERS:
          serviceImpl.searchOrders((com.google.protobuf.StringValue) request,
              (io.grpc.stub.StreamObserver<me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.Order>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class OrderManagementBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    OrderManagementBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("OrderManagement");
    }
  }

  private static final class OrderManagementFileDescriptorSupplier
      extends OrderManagementBaseDescriptorSupplier {
    OrderManagementFileDescriptorSupplier() {}
  }

  private static final class OrderManagementMethodDescriptorSupplier
      extends OrderManagementBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    OrderManagementMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (OrderManagementGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new OrderManagementFileDescriptorSupplier())
              .addMethod(getGetOrderMethod())
              .addMethod(getSearchOrdersMethod())
              .build();
        }
      }
    }
    return result;
  }
}
