package team03.airdnb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaAuditing
public class AirdnbApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirdnbApplication.class, args);
	}

	// "No beans of 'RestTemplate' type found" 오류 해결
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
