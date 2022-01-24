package it.kekw.clowngg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:applicationcontext.xml")
public class ClownggApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(ClownggApplication.class, args);
	}

}
