package it.aleph.omega.exception.grpc.impl;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;
import it.aleph.omega.exception.NotFoundException;
import it.aleph.omega.exception.grpc.AbstractGrpcExceptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

@Component
@RequiredArgsConstructor
public class ResourceNotFoundMapper extends AbstractGrpcExceptionMapper {

    private final static String MISSING_ID_LIST_KEY = "missing_id_list";
    private final Metadata metadata;
    @Override
    public void mapException(Throwable throwable, StreamObserver<?> observer) {
        if(throwable instanceof NotFoundException) {
            observer.onError(Status.NOT_FOUND.withCause(throwable)
                    .withDescription(throwable.getMessage())
                    .asRuntimeException(buildMetadata(metadata, throwable)));
        }else{
            super.mapException(throwable, observer);
        }
    }

    @Override
    public Metadata buildMetadata(Metadata metadata, Throwable throwable) {
        NotFoundException ex = (NotFoundException) throwable;
        ex.getIdListNotFound().forEach(id -> metadata
                .put(Metadata.Key.of(MISSING_ID_LIST_KEY, ASCII_STRING_MARSHALLER), id.toString()));
        return metadata;
    }
}
