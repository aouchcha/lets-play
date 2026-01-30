package Lets_play.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import Lets_play.Backend.Configs.Jwt.Role;
import Lets_play.Backend.Model.User;
import Lets_play.Backend.Repository.userRepository;

@SpringBootApplication
public class BackendApplication {

	private final userRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public BackendApplication(userRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void initData() {
		if (userRepository.findByUsername("admin") == null) {
			User Admin = new User();
			Admin.setEmail("admin@gmail.com");
			Admin.setPassword(passwordEncoder.encode("admin"));
			Admin.setUsername("admin");
			Admin.setRole(Role.Admin.toString());
			userRepository.save(Admin);
		}
	}

}
