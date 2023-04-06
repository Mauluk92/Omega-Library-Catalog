package it.aleph.omega.exception.grpc.impl;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import it.aleph.omega.exception.ResourceNotFoundException;
import it.aleph.omega.exception.grpc.GrpcExceptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Qualifier(value = "responseStatusMapper")
@RequiredArgsConstructor
public class ResourceNotFoundMapper implements GrpcExceptionMapper {

    private GrpcExceptionMapper grpcExceptionMapper;


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
            Optional
                    .ofNullable(grpcExceptionMapper)
                    .ifPresentOrElse(
                            mapper -> mapper.mapException(throwable, observer),
                            () -> onDefault(observer));
        }
    }
}
