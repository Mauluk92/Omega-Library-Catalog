package it.aleph.omega.config;

import book.BookProtoServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class ConfigApplication {

    private final BookProtoServiceGrpc.BookProtoServiceImplBase grpcService;

    @Bean
    public Server getServerBean() throws IOException {
        Server server = ServerBuilder.forPort(9090).addService(grpcService).build();
        server.start();
        return server;
    }
}
