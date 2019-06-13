package com.prism.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin2.server.internal.EnableZipkinServer;

/*
 * @EnableZipkinServer、@EnableZipkinStreamServer两者二选一
 * 通过源码可看到，@EnableZipkinStreamServer包含了@EnableZipkinServer，同时
 * 还创建了一个rabbit-mq的消息队列监听器，所以也支持原来的HTTP通信方式
 */
//@EnableZipkinServer//默认采用HTTP通信方式启动ZipkinServer
//@EnableZipkinStreamServer//采用Stream通信方式启动ZipkinServer，也支持HTTP通信方式
@SpringBootApplication
@EnableDiscoveryClient
@EnableZipkinServer
public class CenterZipkinApplication {

	public static void main(String[] args) {
		SpringApplication.run(CenterZipkinApplication.class, args);
	}

}
