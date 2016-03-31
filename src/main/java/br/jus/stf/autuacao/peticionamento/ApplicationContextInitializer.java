package br.jus.stf.autuacao.peticionamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 18.12.2015
 */
@SpringBootApplication(scanBasePackages = "br.jus.stf")
public class ApplicationContextInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationContextInitializer.class, args);
	}
	
}
