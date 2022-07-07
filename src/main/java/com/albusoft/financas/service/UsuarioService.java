package com.albusoft.financas.service;

import java.util.List;
import java.util.Optional;

import com.albusoft.financas.exception.RegraNegocioException;
import com.albusoft.financas.model.entity.Usuario;

public interface UsuarioService {

	Usuario salvar(Usuario usuario) throws RegraNegocioException;
	void validarEmail(String email);
	List<Usuario> findAll();
	void deletar(Usuario usuario);
	Usuario autenticarUsuario(String email, String senha);
	
}
