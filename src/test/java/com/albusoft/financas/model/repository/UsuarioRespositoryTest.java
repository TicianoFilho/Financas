package com.albusoft.financas.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.albusoft.financas.model.entity.Usuario;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class UsuarioRespositoryTest {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Test
	public void deveVerificarExistenciaDeEmail() {
		
		//Cenário
		Usuario usuario = Usuario.builder()
				.nome("Ticiano")
				.email("ticianofilho@gmamil.com")
				.senha("123").build();
		
		usuarioRepository.save(usuario);
		
		//Ação / Execução
		boolean result = usuarioRepository.existsByEmail(usuario.getEmail());
		
		//Verificação
		Assertions.assertThat(result).isTrue();
		
	}
	
	@Test
	public void deveRetornarFalsoSeNaoExisteUsuarioComEmail() {
		usuarioRepository.deleteAll();
		Assertions.assertThat(usuarioRepository.existsByEmail("ticianofilho@gmail.com")).isFalse();
		
	}
}
