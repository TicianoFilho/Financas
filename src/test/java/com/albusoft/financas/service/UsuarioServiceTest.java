package com.albusoft.financas.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.albusoft.financas.exception.AutenticacaoUsuarioException;
import com.albusoft.financas.exception.RegraNegocioException;
import com.albusoft.financas.model.entity.Usuario;
import com.albusoft.financas.model.repository.UsuarioRepository;
import com.albusoft.financas.service.impl.UsuarioServiceImpl;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	UsuarioService usuarioService;
	UsuarioRepository usuarioRepository;
	
	@BeforeEach
	public void setUp() {
		usuarioRepository = Mockito.mock(UsuarioRepository.class);
		usuarioService = new UsuarioServiceImpl(usuarioRepository);
	}
	
	
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
		Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		//Ação
		Assertions.assertDoesNotThrow(() -> usuarioService.validarEmail("ticianofilho@gmail.com"));
	}
	
	@Test
	public void salvarDeveSalvarOUsuario() {
	
		
		Usuario usuario = Usuario.builder().nome("Theo").email("theo@gmail.com").senha("123").build();
		
		Usuario usuarioSalvo = usuarioService.salvar(usuario);
		
		Assertions.assertTrue(!String.valueOf(usuarioSalvo.getId()).isBlank());
		
	}
	
	@Test
	public void autenticarDeveTrazerEmailUsuario() {
		usuarioRepository.deleteAll();
		Usuario usuario = Usuario.builder().nome("Theo").email("theo@gmail.com").senha("123").build();
		usuarioService.salvar(usuario);
		
		Optional<Usuario> usuarioBusca = usuarioRepository.findByEmail(usuario.getEmail());
		
		Assertions.assertTrue(usuarioBusca.isPresent());
	}
	
	@Test
	public void autenticarNaoDeveTrazerEmailUsuario() {
		usuarioRepository.deleteAll();
		
		Optional<Usuario> usuarioBusca = usuarioRepository.findByEmail("theo@gmail.com");
		
		Assertions.assertFalse(usuarioBusca.isPresent());
	}
	
	@Test
	public void autenticarUsuarioDeveNaoLancarExcecao() {
		usuarioRepository.deleteAll();
		Usuario usuario = Usuario.builder().nome("Theo").email("theo@gmail.com").senha("123").build();
		usuarioService.salvar(usuario);
		
		Assertions.assertDoesNotThrow(() -> {
			usuarioService.autenticarUsuario(usuario.getEmail(), usuario.getSenha());
		});
	}
	
	@Test
	public void autenticarUsuarioDeveLancarExcecaoEmailErrado() {
		usuarioRepository.deleteAll();
		Usuario usuario = Usuario.builder().nome("Theo").email("theo@gmail.com").senha("123").build();
		usuarioService.salvar(usuario);
		
		Assertions.assertThrows(AutenticacaoUsuarioException.class, () -> {
			usuarioService.autenticarUsuario("email.errado@gmail.com", usuario.getSenha());
		});
	}
	
	@Test
	public void autenticarUsuarioDeveLancarExcecaoSenhaErrada() {
		usuarioRepository.deleteAll();
		Usuario usuario = Usuario.builder().nome("Theo").email("theo@gmail.com").senha("123").build();
		usuarioService.salvar(usuario);
		
		Assertions.assertThrows(AutenticacaoUsuarioException.class, () -> {
			usuarioService.autenticarUsuario(usuario.getEmail(), "erro123");
		});
	}
	
	
}
