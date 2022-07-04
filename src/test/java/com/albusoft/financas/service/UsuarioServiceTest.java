package com.albusoft.financas.service;

import java.util.Optional;

import org.hamcrest.MatcherAssert;
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
	
	@Test
	public void salvarDeveSalvarOUsuario() {
		
		usuarioRepository.deleteAll();
		
		Usuario usuario = Usuario.builder().nome("Theo").email("theo@gmail.com").senha("123").build();
		
		Usuario usuarioSalvo = usuarioService.salvar(usuario);
		
		Assertions.assertTrue(!String.valueOf(usuarioSalvo.getId()).isBlank());
		
	}
	
	@Test
	public void autenticarVerificaSeTrazEmailUsuarioDeveTrazer() {
		usuarioRepository.deleteAll();
		Usuario usuario = Usuario.builder().nome("Theo").email("theo@gmail.com").senha("123").build();
		usuarioService.salvar(usuario);
		
		Optional<Usuario> usuarioBusca = usuarioRepository.findByEmail(usuario.getEmail());
		
		Assertions.assertTrue(usuarioBusca.isPresent());
	}
	
	
}
