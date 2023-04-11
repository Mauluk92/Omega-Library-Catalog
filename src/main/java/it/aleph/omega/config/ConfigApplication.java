package it.aleph.omega.config;

import book.BookProtoServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import it.aleph.omega.exception.grpc.AbstractGrpcExceptionMapper;
import it.aleph.omega.exception.grpc.GrpcExceptionMapper;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.util.List;

@Configuration
public class ConfigApplication {

    @Bean
    public Server getServerBean(BookProtoServiceGrpc.BookProtoServiceImplBase grpcService,@Value("${grpc.server.port}") Integer grpcPort) throws IOException {
        Server server = ServerBuilder.forPort(grpcPort).addService(grpcService).build();
        server.start();
        return server;
    }

    /**
     * This is the bean configuration for the chain of responsibility class for grpc errors
     * Each time a new handler is needed, it is built up from here via the GrpcExceptionMapperBuilder interface
     * by calling addLink method with the appropriate grpcExceptionMapper.
     * This allows for extensible exception handling by successively adding a new Mapper to the chain.
     * @return the full-featured GrpcExceptionMapper chain of responsibility
     */
    @Bean
    @Primary
    public GrpcExceptionMapper getExceptionMapper(List<AbstractGrpcExceptionMapper> builders){
        return builders
                .stream()
                .reduce(AbstractGrpcExceptionMapper::addLink)
                .orElseThrow(
                        () -> new BeanInstantiationException(GrpcExceptionMapper.class,
                                "Could not Instantiate GrpcExceptionMapper"));
    }
}
