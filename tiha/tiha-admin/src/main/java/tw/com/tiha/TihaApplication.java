package tw.com.tiha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ComponentScan("tw.com.tiha")
@SpringBootApplication
public class TihaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TihaApplication.class, args);
	}

}
