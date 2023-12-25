package me.zeroest.grpc.ordermgt;

import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import me.zeroest.grpc.ecommerce.v1.OrderManagementGrpc;
import me.zeroest.grpc.ecommerce.v1.OrderManagementOuterClass;

import java.util.Iterator;
import java.util.logging.Logger;

public class OrderMgtClient {

    private static final Logger logger = Logger.getLogger(OrderMgtClient.class.getName());

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        OrderManagementGrpc.OrderManagementBlockingStub stub = OrderManagementGrpc.newBlockingStub(channel);

//        getOrder(stub);
        searchOrders(stub);
    }

    private static void getOrder(OrderManagementGrpc.OrderManagementBlockingStub stub) {
        StringValue id = StringValue.newBuilder().setValue("102").build();
        OrderManagementOuterClass.Order orderResponse = stub.getOrder(id);
        logger.info("GetOrder Response -> : " + orderResponse.toString());
    }

    private static void searchOrders(OrderManagementGrpc.OrderManagementBlockingStub stub) {
        StringValue searchStr = StringValue.newBuilder().setValue("Google").build();
        Iterator<OrderManagementOuterClass.Order> matchingOrdersItr;
        matchingOrdersItr = stub.searchOrders(searchStr);
        while (matchingOrdersItr.hasNext()) {
            OrderManagementOuterClass.Order matchingOrder = matchingOrdersItr.next();
            logger.info("Search Order Response -> Matching Order - " + matchingOrder.getId());
//            logger.info(" Order : " + order.getId() + "\n " + matchingOrder.toString());
        }
    }

}
