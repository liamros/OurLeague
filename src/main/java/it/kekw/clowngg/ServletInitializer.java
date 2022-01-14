package it.kekw.clowngg;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = "classpath:applicationcontext.xml")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ClownggApplication.class);
	}

}
