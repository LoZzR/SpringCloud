package com.discovery.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.io.IOException;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {
	private static Logger logger = LoggerFactory.getLogger(DiscoveryServerApplication.class);
	public static void main(String... args) throws IOException {
		// Look for configuration in discovery.properties or discoveryserver.yml
		System.setProperty("spring.config.name", "discovery-server");
		var ctx = SpringApplication.run(DiscoveryServerApplication.class, args);
		assert (ctx != null);
		logger.info("Started ...");
		System.in.read();
		ctx.close();
	}
}
