package com.hogwai.spring_jcache_ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(basePackages = "com.hogwai.spring_jcache_ehcache.repository")
public class SpringJcacheEhcacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJcacheEhcacheApplication.class, args);
	}

}
