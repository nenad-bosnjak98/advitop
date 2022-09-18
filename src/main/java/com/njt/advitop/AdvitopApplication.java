package com.njt.advitop;

import com.njt.advitop.configuration.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class AdvitopApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvitopApplication.class, args);
	}

}
