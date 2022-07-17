package com.albusoft.financas.service.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albusoft.financas.exception.AutenticacaoUsuarioException;
import com.albusoft.financas.exception.RegraNegocioException;
import com.albusoft.financas.mensagens.Mensagem;
import com.albusoft.financas.model.entity.Usuario;
import com.albusoft.financas.model.repository.UsuarioRepository;
import com.albusoft.financas.service.UsuarioService;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository usuarioRepository;
	
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public Usuario salvar(@Valid Usuario usuario) throws RegraNegocioException {
		validarEmail(usuario.getEmail());
		return usuarioRepository.save(usuario);
	}

	//Verifica se já existe o email informado cadastrado no banco
	@Override
	public void validarEmail(String email) {
		boolean existe = usuarioRepository.existsByEmail(email);
		if (existe) {
			throw new RegraNegocioException(Mensagem.EMAIL_EXISTE);
		}
	}

	@Override
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	@Override
	public void deletar(Usuario usuario) {
		usuarioRepository.delete(usuario);
	}

	@Override
	public Usuario autenticarUsuario(String email, String senha) {
		
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		
		if (!usuario.isPresent()) {
			throw new AutenticacaoUsuarioException(Mensagem.EMAIL_USUARIO_NAO_ENCONTRADO);
		}
		
		if (!usuario.get().getSenha().equals(senha)) {
			throw new AutenticacaoUsuarioException(Mensagem.SENHA_INVALIDA);
		}
		
		return usuario.get();
	}

	@Override
	public Optional<Usuario> buscarPeloId(int id) {
		
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
		
		if (!usuarioOptional.isPresent()) {
			throw new RegraNegocioException(Mensagem.USUARIO_NAO_EXISTE);
		}
		
		return usuarioOptional;
	}

	

	
}
