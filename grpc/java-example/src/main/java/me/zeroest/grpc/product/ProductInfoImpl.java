package me.zeroest.grpc.product;

import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import me.zeroest.grpc.ecommerce.v1.ProductInfoGrpc;
import me.zeroest.grpc.ecommerce.v1.ProductInfoOuterClass;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class ProductInfoImpl extends ProductInfoGrpc.ProductInfoImplBase { // 플러그인에 의해 생성된 추상 클래스를 확장

    private static final Logger logger = Logger.getLogger(ProductInfoImpl.class.getName());

    private final Map<String, ProductInfoOuterClass.Product> productMap = new ConcurrentHashMap<>();

    @Override
    public void addProduct(ProductInfoOuterClass.Product request, StreamObserver<ProductInfoOuterClass.ProductID> responseObserver) { // responseObserver 객체는 클라이언트에게 응답을 보내고 스트림을 닫는 데 사용된다
        logger.info("ProductInfoImpl.addProduct | product: " + request);

        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        request = request.toBuilder().setId(randomUUIDString).build();
        productMap.put(randomUUIDString, request);
        ProductInfoOuterClass.ProductID id
                = ProductInfoOuterClass.ProductID.newBuilder().setValue(randomUUIDString).build();

        logger.info("ProductInfoImpl.addProduct | productID: " + id);
        responseObserver.onNext(id); // 클라이언트에게 응답을 보낸다
        responseObserver.onCompleted(); // 스트림을 닫아 클라이언트 호출을 종료한다
    }

    @Override
    public void getProduct(ProductInfoOuterClass.ProductID request, StreamObserver<ProductInfoOuterClass.Product> responseObserver) {
        logger.info("ProductInfoImpl.getProduct | productID: " + request);

        String id = request.getValue();
        if (productMap.containsKey(id)) {
            responseObserver.onNext(productMap.get(id));
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new StatusException(Status.NOT_FOUND)); // 클라이언트에게 에러를 보낸다
        }
    }
}
