package tw.org.organ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ComponentScan("tw.org.organ")
@SpringBootApplication
public class OrganApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrganApplication.class, args);
	}

}
