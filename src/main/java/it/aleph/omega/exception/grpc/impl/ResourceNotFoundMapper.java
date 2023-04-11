package it.aleph.omega.exception.grpc.impl;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import it.aleph.omega.exception.NotFoundException;
import it.aleph.omega.exception.grpc.AbstractGrpcExceptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

@Component
@RequiredArgsConstructor
public class ResourceNotFoundMapper extends AbstractGrpcExceptionMapper {

    private final static String MISSING_ID_LIST_KEY = "missing_id_list";
    @Override
    public void mapException(Throwable throwable, StreamObserver<?> observer) {
        if(throwable instanceof NotFoundException) {
            observer.onError(Status.NOT_FOUND.withCause(throwable)
                    .withDescription(throwable.getMessage())
                    .asRuntimeException(buildMetadata(throwable)));
        }else{
            super.mapException(throwable, observer);
        }
    }

    @Override
    public Metadata buildMetadata(Throwable throwable) {
        Metadata metadata = new Metadata();
        NotFoundException ex = (NotFoundException) throwable;
        ex.getIdListNotFound().forEach(id -> metadata
                .put(Metadata.Key.of(MISSING_ID_LIST_KEY, ASCII_STRING_MARSHALLER), id.toString()));
        return metadata;
    }
}
