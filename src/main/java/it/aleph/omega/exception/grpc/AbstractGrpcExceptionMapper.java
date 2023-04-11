package it.aleph.omega.exception.grpc;

import io.grpc.Metadata;
import io.grpc.stub.StreamObserver;
import it.aleph.omega.exception.grpc.builder.GrpcExceptionMapperBuilder;
import it.aleph.omega.exception.grpc.impl.ResourceNotFoundMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Optional;

/**
 * This abstract base class contains the common logic for all the handlers.
 * Each handler should extend this Abstract Mapper.
 * Configuration of the chain of responsibility is done via ConfigApplication class
 * @author Nicola Rossi
 */
public abstract class AbstractGrpcExceptionMapper implements GrpcExceptionMapper {

    private final static String KEY_ERRORS = "errors";

    private AbstractGrpcExceptionMapper grpcExceptionMapper;

    /**
     * This adds the link to the chain of responsibility, otherwise skips to the next link and
     * performs the same operation
     * @param abstractMapper the mapper to be added to the chain
     * @return the built chain object
     */
    @Override
    public AbstractGrpcExceptionMapper addLink(@NonNull AbstractGrpcExceptionMapper abstractMapper) {
        Optional.ofNullable(grpcExceptionMapper)
                .ifPresentOrElse(
                        (action) -> action.addLink(abstractMapper),
                        () -> grpcExceptionMapper = abstractMapper);
        return this;
    }

    /**
     * The mapper function which evaluates the throwable in its implementing subclasses
     * and responsible for converting into the appropriate Status Runtime Exception to be passed
     * to the observer stream
     * @param throwable the throwable passed as argument by the service
     * @param observer the response observer used to pass the error to the grpc
     */

    @Override
    public void mapException(Throwable throwable, StreamObserver<?> observer) {
        Optional
                .ofNullable(grpcExceptionMapper)
                .ifPresentOrElse(
                        mapper -> mapper.mapException(throwable, observer),
                        () -> onDefault(observer));
    }

    /**
     * Metadata builder for further information processing. This is the standard method,
     * but each subclass can implement its own metadata configurations
     * @param metadata Metadata to be put in the error response
     * @param throwable The throwable to be caught
     * @return fully configured Metadata for the grpc error response
     */
    @Override
    public Metadata buildMetadata(Metadata metadata, Throwable throwable){
        metadata.put(Metadata.Key.of(KEY_ERRORS, Metadata.ASCII_STRING_MARSHALLER),
                throwable.getMessage());
        return metadata;
    }
}
