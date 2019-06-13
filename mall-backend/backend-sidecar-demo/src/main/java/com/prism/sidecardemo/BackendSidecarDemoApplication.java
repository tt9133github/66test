package com.prism.sidecardemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;

@SpringBootApplication
@EnableSidecar
public class BackendSidecarDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendSidecarDemoApplication.class, args);
	}

}
