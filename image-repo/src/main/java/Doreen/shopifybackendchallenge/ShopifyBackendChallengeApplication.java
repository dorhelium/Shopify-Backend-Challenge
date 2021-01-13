package Doreen.shopifybackendchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.security.Principal;


@SpringBootApplication
@EnableTransactionManagement
public class ShopifyBackendChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopifyBackendChallengeApplication.class, args);
	}


}
