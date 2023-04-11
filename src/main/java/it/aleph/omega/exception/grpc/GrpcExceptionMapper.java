package it.aleph.omega.exception.grpc;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import it.aleph.omega.exception.grpc.builder.GrpcExceptionMapperBuilder;

/**
 * This interface provides two methods, one for effectively handling the exception grpc-side
 * and the other as a fallback when no link in the chain can handle the exception
 * @author Nicola Rossi
 */
public interface GrpcExceptionMapper extends GrpcExceptionMapperBuilder {

    void mapException(Throwable throwable, StreamObserver<?> observer);

    Metadata buildMetadata(Throwable throwable);

    default void onDefault(StreamObserver<?> observer){
        observer.onError(Status.INTERNAL.asRuntimeException());
    }
}
