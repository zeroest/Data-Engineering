package me.zeroest.grpc.ordermgt;

import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import me.zeroest.grpc.ecommerce.v1.OrderManagementGrpc;
import me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class OrderMgtClient {

    private static final Logger logger = Logger.getLogger(OrderMgtClient.class.getName());

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();
//        OrderManagementGrpc.OrderManagementBlockingStub stub = OrderManagementGrpc.newBlockingStub(channel);
        OrderManagementGrpc.OrderManagementStub asyncStub = OrderManagementGrpc.newStub(channel);

//        getOrder(stub);
//        searchOrders(stub);
        updateOrders(asyncStub);
    }

    private static void getOrder(OrderManagementGrpc.OrderManagementBlockingStub stub) {
        StringValue id = StringValue.newBuilder().setValue("102").build();
        OrderManagementOuterClass.Order orderResponse = stub.getOrder(id);
        logger.info("GetOrder Response -> : " + orderResponse.toString());
    }

    private static void searchOrders(OrderManagementGrpc.OrderManagementBlockingStub stub) {
        StringValue searchStr = StringValue.newBuilder().setValue("Google").build();
        Iterator<OrderManagementOuterClass.Order> matchingOrdersItr = stub.searchOrders(searchStr);
        while (matchingOrdersItr.hasNext()) {
            OrderManagementOuterClass.Order matchingOrder = matchingOrdersItr.next();
            logger.info("Search Order Response -> Matching Order - " + matchingOrder.getId());
//            logger.info(" Order : " + order.getId() + "\n " + matchingOrder.toString());
        }
    }

    private static void updateOrders(OrderManagementGrpc.OrderManagementStub asyncStub) {

        OrderManagementOuterClass.Order updOrder1 = OrderManagementOuterClass.Order.newBuilder()
                .setId("102")
                .addItems("Google Pixel 3A").addItems("Google Pixel Book")
                .setDestination("Mountain View, CA")
                .setPrice(1100)
                .build();
        OrderManagementOuterClass.Order updOrder2 = OrderManagementOuterClass.Order.newBuilder()
                .setId("103")
                .addItems("Apple Watch S4").addItems("Mac Book Pro").addItems("iPad Pro")
                .setDestination("San Jose, CA")
                .setPrice(2800)
                .build();
        OrderManagementOuterClass.Order updOrder3 = OrderManagementOuterClass.Order.newBuilder()
                .setId("104")
                .addItems("Google Home Mini").addItems("Google Nest Hub").addItems("iPad Mini")
                .setDestination("Mountain View, CA")
                .setPrice(2200)
                .build();

        final CountDownLatch finishLatch = new CountDownLatch(1);

        StreamObserver<StringValue> updateOrderResponseObserver = new StreamObserver<StringValue>() {
            @Override
            public void onNext(StringValue value) {
                logger.info("Update Orders Res : " + value.getValue());
            }

            @Override
            public void onError(Throwable t) {
                logger.info("Order ID update error " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                logger.info("Update orders response  completed!");
                finishLatch.countDown();
            }
        };

        StreamObserver<OrderManagementOuterClass.Order> updateOrderRequestObserver = asyncStub.updateOrders(updateOrderResponseObserver);
        updateOrderRequestObserver.onNext(updOrder1);
        sleep(1000);
        updateOrderRequestObserver.onNext(updOrder2);
        sleep(1000);
        updateOrderRequestObserver.onNext(updOrder3);
        sleep(1000);
        updateOrderRequestObserver.onNext(updOrder3);

        if (finishLatch.getCount() == 0) {
            logger.warning("RPC completed or errored before we finished sending.");
            return;
        }
        updateOrderRequestObserver.onCompleted();

        // Receiving happens asynchronously

        try {
            if (!finishLatch.await(10, TimeUnit.SECONDS)) {
                logger.warning("FAILED : Process orders cannot finish within 10 seconds");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
