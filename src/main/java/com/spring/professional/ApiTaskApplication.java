package com.spring.professional;

import com.spring.professional.dto.UserRequest;
import com.spring.professional.models.User;
import com.spring.professional.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiTaskApplication implements CommandLineRunner {

	/*@Autowired
	private UserService  userService;*/

	public static void main(String[] args) {
		SpringApplication.run(ApiTaskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		/*UserRequest userRequest = new UserRequest();
		userRequest.setNick("elmo");
		userRequest.setPassword("lala");
		userService.saveUser(userRequest);*/
	}

	/*@Bean
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(3000);
		factory.setReadTimeout(3000);
		return new RestTemplate(factory);
	}*/

}
