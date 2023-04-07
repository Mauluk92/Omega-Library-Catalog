package it.aleph.omega.exception.grpc.impl;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import it.aleph.omega.exception.ResourceNotFoundException;
import it.aleph.omega.exception.grpc.AbstractGrpcExceptionMapper;
import lombok.Builder;

@Builder
public class ResourceNotFoundMapper extends AbstractGrpcExceptionMapper {


    @Override
    public void mapException(Throwable throwable, StreamObserver<?> observer) {
        if(throwable instanceof ResourceNotFoundException) {
            Metadata metadata = new Metadata();
            metadata.put(Metadata.Key.of("errors", Metadata.ASCII_STRING_MARSHALLER),
                    throwable.getMessage());
            observer.onError(Status.NOT_FOUND.withCause(throwable)
                    .withDescription(throwable.getMessage())
                    .asRuntimeException(metadata));
        }else{
            super.mapException(throwable, observer);
        }
    }
}
