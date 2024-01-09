package io.github.sql1freitas.MyImgApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MyImgApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyImgApiApplication.class, args);
	}

}
