package it.aleph.omega.grpc;

import book.Book;
import book.BookProtoServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import it.aleph.omega.mapper.BookGrpcMapper;
import it.aleph.omega.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Component
public class CatalogGrpcServer extends BookProtoServiceGrpc.BookProtoServiceImplBase {
    private final BookService bookService;
    private final BookGrpcMapper bookGrpcMapper;

    @Override
    public void setAvailableBookStream(Book.BookPatchRequest request, StreamObserver<Empty> responseObserver) {
        try{
            bookService.patchBooks(bookGrpcMapper.toDto(request));
        }catch(ResponseStatusException ex){
            Metadata metadata = new Metadata();
            metadata.put(Metadata.Key.of("errors",Metadata.ASCII_STRING_MARSHALLER), ex.getMessage());
            responseObserver.onError(Status.NOT_FOUND.withCause(ex).withDescription(ex.getMessage()).asRuntimeException(metadata));
        }
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
