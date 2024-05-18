package com.example.aswe.linkopharm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class LinkopharmApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkopharmApplication.class, args);
	}

}
