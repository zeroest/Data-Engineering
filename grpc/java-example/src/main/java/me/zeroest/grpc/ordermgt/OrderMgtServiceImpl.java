package me.zeroest.grpc.ordermgt;

import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import me.zeroest.grpc.ecommerce.v1.OrderManagementGrpc;
import me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderMgtServiceImpl extends OrderManagementGrpc.OrderManagementImplBase {

    private static final Logger logger = Logger.getLogger(OrderMgtServiceImpl.class.getName());

    private OrderManagementOuterClass.Order ord1 = OrderManagementOuterClass.Order.newBuilder()
            .setId("102")
            .addItems("Google Pixel 3A").addItems("Mac Book Pro")
            .setDestination("Mountain View, CA")
            .setPrice(1800)
            .build();
    private OrderManagementOuterClass.Order ord2 = OrderManagementOuterClass.Order.newBuilder()
            .setId("103")
            .addItems("Apple Watch S4")
            .setDestination("San Jose, CA")
            .setPrice(400)
            .build();
    private OrderManagementOuterClass.Order ord3 = OrderManagementOuterClass.Order.newBuilder()
            .setId("104")
            .addItems("Google Home Mini").addItems("Google Nest Hub")
            .setDestination("Mountain View, CA")
            .setPrice(400)
            .build();
    private OrderManagementOuterClass.Order ord4 = OrderManagementOuterClass.Order.newBuilder()
            .setId("105")
            .addItems("Amazon Echo")
            .setDestination("San Jose, CA")
            .setPrice(30)
            .build();
    private OrderManagementOuterClass.Order ord5 = OrderManagementOuterClass.Order.newBuilder()
            .setId("106")
            .addItems("Amazon Echo").addItems("Apple iPhone XS")
            .setDestination("Mountain View, CA")
            .setPrice(300)
            .build();

    private Map<String, OrderManagementOuterClass.Order> orderMap = Stream.of(
            new AbstractMap.SimpleEntry<>(ord1.getId(), ord1),
            new AbstractMap.SimpleEntry<>(ord2.getId(), ord2),
            new AbstractMap.SimpleEntry<>(ord3.getId(), ord3),
            new AbstractMap.SimpleEntry<>(ord4.getId(), ord4),
            new AbstractMap.SimpleEntry<>(ord5.getId(), ord5))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    // Unary
    @Override
    public void getOrder(StringValue request, StreamObserver<OrderManagementOuterClass.Order> responseObserver) {
        OrderManagementOuterClass.Order order = orderMap.get(request.getValue());
        if (order != null) {
            System.out.printf("Order Retrieved : ID - %s", order.getId());
            responseObserver.onNext(order);
            responseObserver.onCompleted();
        } else  {
            logger.info("Order : " + request.getValue() + " - Not found.");
            responseObserver.onCompleted();
        }
        // ToDo  Handle errors
        // responseObserver.onError();
    }

    // Server Streaming
    @Override
    public void searchOrders(StringValue request, StreamObserver<OrderManagementOuterClass.Order> responseObserver) {

        for (Map.Entry<String, OrderManagementOuterClass.Order> orderEntry : orderMap.entrySet()) {
            sleep(1000);

            OrderManagementOuterClass.Order order = orderEntry.getValue();
            int itemsCount = order.getItemsCount();
            for (int index = 0; index < itemsCount; index++) {
                String item = order.getItems(index);
                if (item.contains(request.getValue())) {
                    logger.info("Item found " + item);
                    responseObserver.onNext(order);
                    break;
                }
            }
        }
        responseObserver.onCompleted();
    }

    // Client Streaming
    @Override
    public StreamObserver<OrderManagementOuterClass.Order> updateOrders(StreamObserver<StringValue> responseObserver) {
        return new StreamObserver<OrderManagementOuterClass.Order>() {

            StringBuilder updatedOrderStrBuilder = new StringBuilder().append("Updated Order IDs : ");

            @Override
            public void onNext(OrderManagementOuterClass.Order value) {
                if (value != null) {
                    orderMap.put(value.getId(), value);
                    updatedOrderStrBuilder.append(value.getId()).append(", ");
                    logger.info("Order ID : " + value.getId() + " - Updated");
                }
            }

            @Override
            public void onError(Throwable t) {
                logger.info("Order ID update error " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                logger.info("Update orders - Completed");
                StringValue updatedOrders = StringValue.newBuilder().setValue(updatedOrderStrBuilder.toString()).build();
                responseObserver.onNext(updatedOrders);
                responseObserver.onCompleted();
            }

        };
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
