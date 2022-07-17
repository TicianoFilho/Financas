package com.albusoft.financas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.albusoft.financas.model.entity.Lancamento;
import com.albusoft.financas.model.enums.StatusLancamento;
import com.albusoft.financas.model.enums.TipoLancamento;

public interface LancamentosService {
	
	Lancamento salvar(Lancamento lancamento);
	
	Lancamento editar(Lancamento lancamento);
	
	void deletar(Lancamento lancamento);
	
	List<Lancamento> buscar(Lancamento lancamento);
	
	void atualizarStatus(Lancamento lancamento, StatusLancamento status);
	
	void validar(Lancamento lancamento);
	
	Optional<Lancamento> buscarPorId(int id);
	
	BigDecimal obterSaldoPorUsuario(int usuarioId);

}
