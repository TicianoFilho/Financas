package com.albusoft.financas.model.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.albusoft.financas.model.entity.Lancamento;
import com.albusoft.financas.model.enums.TipoLancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Integer> {

	@Query("select sum(l.valor) from Lancamento l join l.usuario u "
			+ "where u.id = :usuarioId and l.tipoLancamento = :lancamentoTipo group by u")
	public BigDecimal obterSaldoPorUsuario(@Param("usuarioId") int usuarioId, 
			@Param("lancamentoTipo") TipoLancamento tipo);
	
}
