package it.aleph.omega.config;

import book.BookProtoServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import it.aleph.omega.exception.grpc.AbstractGrpcExceptionMapper;
import it.aleph.omega.exception.grpc.GrpcExceptionMapper;
import it.aleph.omega.exception.grpc.impl.ResourceNotFoundMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;

@Configuration
public class ConfigApplication {

    @Value("${grpc.server.port}")
    private Integer GRPC_PORT;

    @Bean
    public Server getServerBean(BookProtoServiceGrpc.BookProtoServiceImplBase grpcService) throws IOException {
        Server server = ServerBuilder.forPort(GRPC_PORT).addService(grpcService).build();
        server.start();
        return server;
    }

    /**
     * This is the bean configuration for the chain of responsibility class for grpc errors
     * Each time a new handler is needed, it is builded up from here via the GrpcExceptionMapperBuilder interface
     * by calling addLink method with the appropriate grpcExceptionMapper.
     * This allows for extensible exception handling by successively adding a new Mapper to the chain.
     * @return the full-featured GrpcExceptionMapper chain of responsibility
     */
    @Bean
    public GrpcExceptionMapper getExceptionMapper(){
        return ResourceNotFoundMapper.builder().build();
    }
}
