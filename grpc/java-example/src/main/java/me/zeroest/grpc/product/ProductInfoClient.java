package me.zeroest.grpc.product;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import me.zeroest.grpc.ecommerce.v1.ProductInfoGrpc;
import me.zeroest.grpc.ecommerce.v1.ProductInfoOuterClass;

import java.util.logging.Logger;

/**
 * gRPC client sample for productInfo service.
 */
public class ProductInfoClient {

    private static final Logger logger = Logger.getLogger(ProductInfoClient.class.getName());

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051) // 연결하려는 서버 주소와 포트를 지정해 gRPC 채널을 만든다
                .usePlaintext() // 평문 사용, 보안되지 않은 연결을 사용
                .build();

        ProductInfoGrpc.ProductInfoBlockingStub stub =
                ProductInfoGrpc.newBlockingStub(channel); // 채널을 사용해 클라이언트 스텁을 생성
        // 두 가지 유형 사용 가능
        // 1. 서버의 응답을 받을 때까지 대기하는 BlockingStub
        // 2. 서버의 응답을 기다리지 않고 옵저버를 등록해 응답 받는 NonBlockingStub

        ProductInfoOuterClass.ProductID productID = stub.addProduct(
                ProductInfoOuterClass.Product.newBuilder()
                        .setName("Samsung S10")
                        .setDescription("Samsung Galaxy S10 is the latest smart phone, " +
                                "launched in February 2019")
                        .setPrice(700.0f)
                        .build());
        logger.info("Product ID: " + productID.getValue() + " added successfully.");

        ProductInfoOuterClass.Product product = stub.getProduct(productID);
        logger.info("Product: " + product.toString());
        channel.shutdown(); // 모든 작업이 완료되면 연결을 종료해 어플리케이션에서 사용한 네트워크 리소스 안전하게 반환
    }

}
