package com.albusoft.financas.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.albusoft.financas.model.enums.StatusLancamento;
import com.albusoft.financas.model.enums.TipoLancamento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lancamento")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotBlank
	private int id;
	
	@NotBlank
	private String descricao;
	
	@NotBlank
	private int mes;
	
	@NotBlank
	private int ano;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	@NotBlank
	private Usuario usuario;
	
	@NotBlank
	private BigDecimal valor;
	
	@Column(name = "data_cadastro")
	private LocalDate dataCadastro = LocalDate.now();
	
	@Column(name = "tipo_lanc")
	@Enumerated(value = EnumType.ORDINAL)
	@NotBlank
	private TipoLancamento tipoLancamento;	
	
	@Column(name = "status_lanc")
	@Enumerated(value = EnumType.ORDINAL)
	@NotBlank
	private StatusLancamento statusLancamento;
}