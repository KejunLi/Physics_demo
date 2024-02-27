package edu.ucsc.physics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("edu.ucsc.physics")
@ComponentScan(basePackages = { "edu.ucsc.physics" })
@EntityScan("edu.ucsc.physics")
public class PhysicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhysicsApplication.class, args);

	}
}
