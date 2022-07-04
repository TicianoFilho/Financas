package com.albusoft.financas.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.albusoft.financas.exception.RegraNegocioException;
import com.albusoft.financas.model.entity.Usuario;
import com.albusoft.financas.model.repository.UsuarioRepository;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Test
	public void validarEmailTemQueLancarExcecao() {
		
		//Cenário
		usuarioRepository.deleteAll();
		Usuario usuario = Usuario.builder().nome("Ticaino").email("ticianofilho@gmail.com").senha("abc").build();
		usuarioService.salvar(usuario);
		
		//Ação
		Assertions.assertThrows(RegraNegocioException.class, () -> usuarioService.validarEmail(usuario.getEmail()));		
		
	}
	
	@Test
	public void validarEmailNaoDeveLancarExcecao() {
		
		//Cenário
		usuarioRepository.deleteAll();
		
		//Ação
		Assertions.assertDoesNotThrow(() -> usuarioService.validarEmail("ticianofilho@gmail.com"));
	}
	
	
}
