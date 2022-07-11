package com.albusoft.financas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.albusoft.financas.api.dto.UsuarioDTO;
import com.albusoft.financas.exception.AutenticacaoUsuarioException;
import com.albusoft.financas.exception.RegraNegocioException;
import com.albusoft.financas.model.entity.Usuario;
import com.albusoft.financas.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class UsuarioController {
	
	UsuarioService usuarioService;
	
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@GetMapping("/autenticar")
	public ResponseEntity autenticarUsuario(@RequestBody UsuarioDTO usuario) {
		try {
			Usuario usuarioAutenticado = usuarioService.autenticarUsuario(usuario.getEmail(), usuario.getSenha());
			return ResponseEntity.ok(usuarioAutenticado);
		} catch (AutenticacaoUsuarioException a) {
			return ResponseEntity.badRequest().body(a.getMessage());
		}
	}
	
	@PostMapping("/usuarios")
	public ResponseEntity salvar(@RequestBody UsuarioDTO usuarioDTO) {
		
		Usuario usuario = Usuario.builder()
				.nome(usuarioDTO.getNome())
				.email(usuarioDTO.getEmail())
				.senha(usuarioDTO.getSenha()).build();
		
		try {
			usuarioService.salvar(usuario);
			return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
		} catch (RegraNegocioException r) {
			return ResponseEntity.badRequest().body(r.getMessage());
		}
		
	}

}
