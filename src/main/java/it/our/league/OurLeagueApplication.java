package it.our.league;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:applicationcontext.xml")
public class OurLeagueApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(OurLeagueApplication.class, args);
	}

}
