package com.cloud.detective;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.io.IOException;

@EntityScan(basePackages = {"com.entities.person", "com.entities.detective"})
@SpringBootApplication
@EnableEurekaClient
public class DetectivesServer {

    private static Logger logger = LoggerFactory.getLogger(DetectivesServer.class);

    public static void main(String... args) throws IOException {
        // Look for configuration in detectives-server.properties or detectives-server.yml
        System.setProperty("spring.config.name", "detectives-server");

        var ctx = SpringApplication.run(DetectivesServer.class, args);
        assert (ctx != null);
        logger.info("Started ...");
        System.in.read();
        ctx.close();
    }
}
