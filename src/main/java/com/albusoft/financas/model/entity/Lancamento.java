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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.albusoft.financas.model.enums.StatusLancamento;
import com.albusoft.financas.model.enums.TipoLancamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lancamento")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	private String descricao;
	
	@Range(min = 1, max = 12)
	private Integer mes;
	
	@Min(1900)
	private Integer ano;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	@NotNull
	private Usuario usuario;
	
	@NotNull
	private BigDecimal valor;
	
	@Column(name = "data_cadastro")
	private LocalDate dataCadastro = LocalDate.now();
	
	@Column(name = "tipo_lanc")
	@Enumerated(value = EnumType.ORDINAL)
	@NotNull
	private TipoLancamento tipoLancamento;	
	
	@Column(name = "status_lanc")
	@Enumerated(value = EnumType.ORDINAL)
	@NotNull
	private StatusLancamento statusLancamento;
}