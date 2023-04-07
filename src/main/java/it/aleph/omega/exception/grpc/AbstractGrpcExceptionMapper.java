package it.aleph.omega.exception.grpc;

import io.grpc.stub.StreamObserver;
import it.aleph.omega.exception.grpc.builder.GrpcExceptionMapperBuilder;
import it.aleph.omega.exception.grpc.impl.ResourceNotFoundMapper;
import lombok.NonNull;

import java.util.Optional;

/**
 * This abstract base class contains the common logic for all the handlers.
 * Each handler should extends this Abstract Mapper.
 * Configuration of the chain of responsibility is done via ConfigApplication class
 * @author Nicola Rossi
 */
public abstract class AbstractGrpcExceptionMapper implements GrpcExceptionMapper {

    private GrpcExceptionMapper grpcExceptionMapper;

    @Override
    public GrpcExceptionMapperBuilder addLink(@NonNull GrpcExceptionMapper grpcExceptionMapper) {
        this.grpcExceptionMapper = grpcExceptionMapper;
        return this.grpcExceptionMapper;
    }

    @Override
    public void mapException(Throwable throwable, StreamObserver<?> observer) {
        Optional
                .ofNullable(grpcExceptionMapper)
                .ifPresentOrElse(
                        mapper -> mapper.mapException(throwable, observer),
                        () -> onDefault(observer));
    }

    @Override
    public GrpcExceptionMapper build() {
        return this;
    }


}
