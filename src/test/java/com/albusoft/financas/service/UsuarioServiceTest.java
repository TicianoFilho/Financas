package com.albusoft.financas.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	@Autowired
	UsuarioService usuarioService;
	
	//@Test(expected = Test.None.class)  //É do JUnit 4, tenho que ver como é implementado no JUnit 5 (para dizer que espera que nenhuma exception seja lançada)
	public void validarEmail() {
		
	}

}
