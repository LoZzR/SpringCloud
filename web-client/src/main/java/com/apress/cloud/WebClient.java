package com.apress.cems.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

@RestController
@SpringBootApplication
@EnableEurekaClient
public class WebClient {

    private static Logger logger = LoggerFactory.getLogger(WebClient.class);

    public static void main(String... args) throws IOException {
        // Look for configuration in  web-client.properties or web-client.yml
        System.setProperty("spring.config.name", "web-client");
        var ctx = SpringApplication.run(WebClient.class, args);
        assert (ctx != null);
        logger.info("Started ...");
        System.in.read();
        ctx.close();
    }

    @Bean @LoadBalanced RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    LoadBalancerClient loadBalancer;

    @GetMapping(value = {"/","/home"})
    public String home() {
        var sb = new StringBuilder();
        getUri(sb, "persons-service");
        getUri(sb, "detectives-service");
        return sb.toString();
    }

    private void getUri(StringBuilder sb, String s) {
        URI uri =  null;
        try {
            var instance = loadBalancer.choose(s);
            uri = instance.getUri();
            logger.debug("Resolved serviceId '{}' to URL '{}'.", s, uri);
            sb.append("Found microservice: ").append(uri.toString()).append("; ");
        } catch (RuntimeException e) {
            logger.warn("Failed to resolve serviceId '{}'. Fallback to URL '{}'.", s, uri);
            sb.append("Not Found microservice ").append(s).append("; ");
        }
    }
}
