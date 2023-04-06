package it.aleph.omega.exception.grpc.impl;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import it.aleph.omega.exception.grpc.GrpcExceptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Qualifier(value = "responseStatusMapper")
@RequiredArgsConstructor
public class ResponseStatusMapper implements GrpcExceptionMapper {

    @Qualifier("defaultThrowableMapper")
    private final GrpcExceptionMapper grpcExceptionMapper;

    @Override
    public void mapException(Throwable throwable, StreamObserver<?> observer) {
        if(throwable instanceof ResponseStatusException){
            Metadata metadata = new Metadata();
            metadata.put(Metadata.Key.of("errors",Metadata.ASCII_STRING_MARSHALLER), throwable.getMessage());
            observer.onError(Status.NOT_FOUND.withCause(throwable).withDescription(throwable.getMessage()).asRuntimeException(metadata));
        }else{
            this.grpcExceptionMapper.mapException(throwable, observer);
        }

    }
}
