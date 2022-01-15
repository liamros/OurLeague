package it.kekw.clowngg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:applicationcontext.xml")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class ClownggApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(ClownggApplication.class, args);
	}

}
