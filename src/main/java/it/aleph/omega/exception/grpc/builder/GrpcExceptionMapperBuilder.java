package it.aleph.omega.exception.grpc.builder;

import it.aleph.omega.exception.grpc.AbstractGrpcExceptionMapper;
import it.aleph.omega.exception.grpc.GrpcExceptionMapper;
import org.springframework.lang.NonNull;

/**
 * This builder is used to instantiate a single GrpcExceptionMapper, while
 * providing the next link in the chain, thus effectively building up a chain of
 * responsibility which handles the exception
 * @author Nicola Rossi
 */
public interface GrpcExceptionMapperBuilder {

    AbstractGrpcExceptionMapper addLink(@NonNull AbstractGrpcExceptionMapper builder);
}
