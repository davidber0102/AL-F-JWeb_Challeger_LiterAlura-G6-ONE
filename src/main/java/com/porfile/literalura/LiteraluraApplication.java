package com.porfile.literalura;

import com.porfile.literalura.Principal.Principal;
import com.porfile.literalura.Repositorio.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private Principal principal;

	public LiteraluraApplication (Principal principal) { this.principal = principal; }

	public static void main(String[] args) {SpringApplication.run(LiteraluraApplication.class, args);	}


	@Override
	public void run(String... args) throws Exception {principal.muestraElMenu();}
}
