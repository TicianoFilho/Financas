package com.albusoft.financas.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albusoft.financas.exception.RegraNegocioException;
import com.albusoft.financas.mensagens.Mensagem;
import com.albusoft.financas.model.entity.Lancamento;
import com.albusoft.financas.model.enums.StatusLancamento;
import com.albusoft.financas.model.enums.TipoLancamento;
import com.albusoft.financas.model.repository.LancamentoRepository;
import com.albusoft.financas.service.LancamentosService;

@Service
public class LancamentoServiceImpl implements LancamentosService {

	private LancamentoRepository lancamentoRepository;
	
	public LancamentoServiceImpl(LancamentoRepository lancamentoRepository) {
		this.lancamentoRepository = lancamentoRepository;
	}
	
	@Override
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		validar(lancamento);
		lancamento.setStatusLancamento(StatusLancamento.PENDENTE);
		return lancamentoRepository.save(lancamento);
	}

	@Override
	@Transactional
	public Lancamento editar(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		return lancamentoRepository.save(lancamento);
	}

	@Override
	@Transactional
	public void deletar(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		lancamentoRepository.delete(lancamento);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lancamento> buscar(Lancamento lancamento) {
		
		Example<Lancamento> example = Example.of(lancamento, 
				ExampleMatcher.matching().withIgnoreCase().
				withStringMatcher(StringMatcher.CONTAINING));
		
		List<Lancamento> test = lancamentoRepository.findAll(example);
		System.out.println(test);
		return test;
	}

	@Override
	@Transactional
	public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
		lancamento.setStatusLancamento(status);
		editar(lancamento);
		
	}

	@Override
	public void validar(Lancamento lancamento) {
		
		if (lancamento.getDescricao().isBlank()) {
			throw new RegraNegocioException(Mensagem.DESCRICAO_LANCAMENTO_VAZIO);
		}
		
		if (lancamento.getMes() < 1 || lancamento.getMes() > 12) {
			throw new RegraNegocioException(Mensagem.MES_LANCAMENTO_INVALIDO);
		}
		
		if (String.valueOf(lancamento.getAno()).length() != 4) {
			throw new RegraNegocioException(Mensagem.ANO_LANCAMENTO_INVALIDO);
		}
		
		if (lancamento.getUsuario() == null || lancamento.getUsuario().getId() <= 0) {
			throw new RegraNegocioException(Mensagem.USUARIO_LANCAMENTO_INVALIDO);
		}
		
		if (lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1) {
			throw new RegraNegocioException(Mensagem.VALOR_MENOR_OU_IGUAL_A_ZERO);
		}
		
		if (lancamento.getTipoLancamento() == null) {
			throw new RegraNegocioException(Mensagem.TIPO_LANCAMENTO_INVALIDO);
		}
		
		
	}

	@Override
	public Optional<Lancamento> buscarPorId(int id) {
		
		Optional<Lancamento> lancamentoOptional = lancamentoRepository.findById(id);
		
		if (!lancamentoOptional.isPresent())
			throw new RegraNegocioException(Mensagem.LANCAMENTO_NAO_ENCONTRADO);
		
		return lancamentoOptional;
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal obterSaldoPorUsuario(int usuarioId) {
		
		BigDecimal receita = lancamentoRepository.obterSaldoPorUsuario(usuarioId, TipoLancamento.RECEITA);
		BigDecimal despesa = lancamentoRepository.obterSaldoPorUsuario(usuarioId, TipoLancamento.DESPESA);
		
		if (receita == null)
			receita = BigDecimal.ZERO;
		
		if (despesa == null) 
			despesa = BigDecimal.ZERO;
		
		return receita.subtract(despesa);
	}

}
