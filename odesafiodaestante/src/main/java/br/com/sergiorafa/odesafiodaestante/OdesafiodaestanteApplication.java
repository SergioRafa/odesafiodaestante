package br.com.sergiorafa.odesafiodaestante;

import br.com.sergiorafa.odesafiodaestante.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OdesafiodaestanteApplication {

	public static void main(String[] args) {
		SpringApplication.run(OdesafiodaestanteApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(Principal principal) {
		return args -> {
			principal.exibeMenu();
		};
	}
}