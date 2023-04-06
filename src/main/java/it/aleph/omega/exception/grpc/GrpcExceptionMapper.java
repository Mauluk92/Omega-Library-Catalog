package it.aleph.omega.exception.grpc;

import io.grpc.stub.StreamObserver;

public interface GrpcExceptionMapper {

    void mapException(Throwable throwable, StreamObserver<?> observer);
}
