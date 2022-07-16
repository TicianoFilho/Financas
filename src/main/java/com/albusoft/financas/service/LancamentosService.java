package com.albusoft.financas.service;

import java.util.List;

import com.albusoft.financas.model.entity.Lancamento;
import com.albusoft.financas.model.enums.StatusLancamento;

public interface LancamentosService {
	
	Lancamento salvar(Lancamento lancamento);
	
	Lancamento editar(Lancamento lancamento);
	
	void deletar(Lancamento lancamento);
	
	List<Lancamento> buscar(Lancamento lancamento);
	
	void atualizarStatus(Lancamento lancamento, StatusLancamento status);
	
	void validar(Lancamento lancamento);

}
