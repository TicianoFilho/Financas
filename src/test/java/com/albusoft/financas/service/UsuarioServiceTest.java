package com.albusoft.financas.service;

import static org.mockito.Mockito.doReturn;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.albusoft.financas.exception.AutenticacaoUsuarioException;
import com.albusoft.financas.exception.RegraNegocioException;
import com.albusoft.financas.model.entity.Usuario;
import com.albusoft.financas.model.repository.UsuarioRepository;
import com.albusoft.financas.service.impl.UsuarioServiceImpl;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)    //para usar a annotation @MockBean
public class UsuarioServiceTest {
	
	@SpyBean
	UsuarioServiceImpl usuarioService;
	@MockBean  
	UsuarioRepository usuarioRepository;
	
	@BeforeEach
	public void setUp() {
		//usuarioRepository = Mockito.mock(UsuarioRepository.class); useless now because of @MockBean annotation
		//usuarioService = Mockito.spy(UsuarioServiceImpl.class); useless now because of @SpyBean annotation
	}
	
	
	@Test
	public void validarEmailTemQueLancarExcecao() {
		
		//Cenário
		Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		//Ação
		Assertions.assertThrows(RegraNegocioException.class, () -> usuarioService.validarEmail("email@email.com"));		
		
	}
	
	@Test
	public void validarEmailNaoDeveLancarExcecao() {
		
		//Cenário
		Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		//Ação
		Assertions.assertDoesNotThrow(() -> usuarioService.validarEmail("email@email.com"));
	}
	
	@Test
	public void salvarDeveSalvarUsuario() {
		
		//método que retorna void
		Mockito.doNothing().when(usuarioService).validarEmail(Mockito.anyString());
		
		Usuario usuario = Usuario.builder().id(1).nome("Theo").email("theo@gmail.com").senha("123").build();
		Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		
		Usuario u = usuarioRepository.save(usuario);
		
		Assertions.assertNotNull(u);
		
	}
	
	@Test
	public void autenticarUsuarioDeveNaoLancarExcecao() {
		
		var theEmail = "theo@gmail.com";
		var theSenha = "123";
		
		//Cenário
		Usuario usuario = Usuario.builder().nome("Theo").email(theEmail).senha(theSenha).build();
		Mockito.when(usuarioRepository.findByEmail(theEmail)).thenReturn(Optional.of(usuario));
		
		//Ação
		Usuario usuarioAutenticado = usuarioService.autenticarUsuario(theEmail, theSenha);
		
		//Verificação
		Assertions.assertNotNull(usuarioAutenticado);
	}
	
	@Test
	public void autenticarUsuarioDeveLancarExcecaoEmailErrado() {
		
		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty()); //irá retornar usuario vazio
		
		Assertions.assertThrows(AutenticacaoUsuarioException.class, () -> {
			usuarioService.autenticarUsuario("email.errado@gmail.com", "qualquerSenha");
		});
	}
	
	@Test
	public void autenticarUsuarioDeveLancarExcecaoSenhaErrada() {
	
		Usuario usuario = Usuario.builder().nome("Theo").email("theo@gmail.com").senha("123").build();
		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario)); //simula um retorno do usuário acima
		
		Assertions.assertThrows(AutenticacaoUsuarioException.class, () -> {
			usuarioService.autenticarUsuario(usuario.getEmail(), "senhaErrada123");
		});
	}
	
	
}
