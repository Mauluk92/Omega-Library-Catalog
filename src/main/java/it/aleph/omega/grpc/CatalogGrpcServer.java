package it.aleph.omega.grpc;

import book.Book;
import book.BookProtoServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import it.aleph.omega.exception.ResourceNotFoundException;
import it.aleph.omega.exception.grpc.GrpcExceptionMapper;
import it.aleph.omega.mapper.BookGrpcMapper;
import it.aleph.omega.service.BookService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Component
@RequiredArgsConstructor
public class CatalogGrpcServer extends BookProtoServiceGrpc.BookProtoServiceImplBase {
    private final BookService bookService;
    private final BookGrpcMapper bookGrpcMapper;
    private final GrpcExceptionMapper grpcExceptionMapper;

    @Override
    public void setAvailableBookStream(Book.BookPatchRequest request, StreamObserver<Empty> responseObserver) {
        try{
            bookService.patchBooks(bookGrpcMapper.toDto(request));
        }catch(ResourceNotFoundException ex){
            grpcExceptionMapper.mapException(ex, responseObserver);
        }
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
