package com.albusoft.financas.service;

import org.springframework.stereotype.Service;

import com.albusoft.financas.model.entity.Usuario;

public interface UsuarioService {

	Usuario autenticar(String usuario, String login);
	Usuario salvar(Usuario usuario);
	void validarEmail(String email);
	
}
