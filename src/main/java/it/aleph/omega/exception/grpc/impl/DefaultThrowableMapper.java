package it.aleph.omega.exception.grpc.impl;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import it.aleph.omega.exception.grpc.GrpcExceptionMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier(value ="defaultThrowableMapper")
public class DefaultThrowableMapper implements GrpcExceptionMapper {

    @Override
    public void mapException(Throwable throwable, StreamObserver<?> observer) {
        observer.onError(Status.INTERNAL.asRuntimeException());
    }
}
