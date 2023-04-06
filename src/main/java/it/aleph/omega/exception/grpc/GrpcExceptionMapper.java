package it.aleph.omega.exception.grpc;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.springframework.http.HttpStatus;

public interface GrpcExceptionMapper {

    void mapException(Throwable throwable, StreamObserver<?> observer);

    default void onDefault(StreamObserver<?> observer){
        observer.onError(Status.INTERNAL.asRuntimeException());
    }
}
