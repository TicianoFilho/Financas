package com.albusoft.financas.api.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LancamentoDTO {
	
	private int id;
	private String descricao;
	private int mes;
	private int ano;
	private BigDecimal valor;
	private int usuario;        //guarda o id do usuário.
	private String tipoLancamento;
	private String statusLancamento;
	
}
