package com.albusoft.financas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.albusoft.financas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Integer> {

}
