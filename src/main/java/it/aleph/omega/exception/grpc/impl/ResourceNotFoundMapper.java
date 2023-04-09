package it.aleph.omega.exception.grpc.impl;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import it.aleph.omega.exception.NotFoundException;
import it.aleph.omega.exception.grpc.AbstractGrpcExceptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResourceNotFoundMapper extends AbstractGrpcExceptionMapper {

    private final Metadata metadata;
    @Override
    public void mapException(Throwable throwable, StreamObserver<?> observer) {
        if(throwable instanceof NotFoundException) {
            observer.onError(Status.NOT_FOUND.withCause(throwable)
                    .withDescription(throwable.getMessage())
                    .asRuntimeException(super.buildMetadata(metadata, throwable)));
        }else{
            super.mapException(throwable, observer);
        }
    }
}
