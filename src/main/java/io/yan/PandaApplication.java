package io.yan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("io.yan.dao")
public class PandaApplication {
	public static void main(String[] args) {
		SpringApplication.run(PandaApplication.class, args);
	}
}
