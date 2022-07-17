package com.albusoft.financas.controller;

import java.util.List;

import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.albusoft.financas.api.dto.AtualizarStatusDTO;
import com.albusoft.financas.api.dto.LancamentoDTO;
import com.albusoft.financas.exception.RegraNegocioException;
import com.albusoft.financas.mensagens.Mensagem;
import com.albusoft.financas.model.entity.Lancamento;
import com.albusoft.financas.model.entity.Usuario;
import com.albusoft.financas.model.enums.StatusLancamento;
import com.albusoft.financas.model.enums.TipoLancamento;
import com.albusoft.financas.service.LancamentosService;
import com.albusoft.financas.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/lancamentos")
public class LancamentoController {

	private LancamentosService lancamentosService;
	private UsuarioService usuarioService;
	
	public LancamentoController(LancamentosService lancamentosService, UsuarioService usuarioService) {
		this.lancamentosService = lancamentosService;
		this.usuarioService = usuarioService;
	}
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody LancamentoDTO lancamentoDTO) {	
	
		try {
			Lancamento lancamento = converterDtoToObj(lancamentoDTO);
			lancamentosService.salvar(lancamento);
			return new ResponseEntity<Lancamento>(lancamento, HttpStatus.CREATED);
		} catch (RegraNegocioException r) {
			return ResponseEntity.badRequest().body(r.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity atualizar(@PathVariable("id") int id, @RequestBody LancamentoDTO lancamentoDTO) {
		
		return lancamentosService.buscarPorId(id).map(entity -> {
			try {
				Lancamento lancamento = converterDtoToObj(lancamentoDTO);
				lancamento.setId(entity.getId());
				lancamentosService.editar(lancamento);
				return ResponseEntity.ok(lancamento);				
			} catch (RegraNegocioException r) {
				return ResponseEntity.badRequest().body(r.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity(Mensagem.LANCAMENTO_NAO_ENCONTRADO, HttpStatus.BAD_REQUEST));		

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deletar(@PathVariable("id") int id) {
		
		return lancamentosService.buscarPorId(id).map( entidade -> {
			try {
				lancamentosService.deletar(entidade);
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			} catch (RegraNegocioException r) {
				return ResponseEntity.badRequest().body(r.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<>(Mensagem.ERRO_AO_DELETAR_LANCAMENTO, HttpStatus.BAD_REQUEST));
		
	}
	
	@GetMapping
	public ResponseEntity buscar(@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "ano", required = false) Integer ano, 
			@RequestParam("usuario") Integer usuarioId) {
		
		Usuario usuario = usuarioService.buscarPeloId(usuarioId)
				.orElseThrow(() -> new RegraNegocioException(Mensagem.USUARIO_NAO_EXISTE));
		
		Lancamento lancamento = Lancamento.builder()
				.descricao(descricao)
				.mes(mes)
				.ano(ano)
				.usuario(usuario)
				.build();
		
		List<Lancamento> lancamentos = lancamentosService.buscar(lancamento);
		
		return ResponseEntity.ok(lancamentos);
	}
	
	@PutMapping("/{id}/atualizar-status")
	public ResponseEntity atualizarStatus(@PathVariable("id") int lancamentoId, @RequestBody AtualizarStatusDTO dto ) {
		
		return lancamentosService.buscarPorId(lancamentoId).map(entity -> {
			
			StatusLancamento statusSelecionado = StatusLancamento.valueOf(dto.getStatus().toUpperCase());
			
			if (statusSelecionado == null)
				return ResponseEntity.badRequest().body(Mensagem.ERRO_AO_ATUALIZAR_STATUS_LANCAMENTO);
			
			try {
				lancamentosService.atualizarStatus(entity, statusSelecionado);
				return ResponseEntity.ok(entity);
			} catch (RegraNegocioException r) {
				return ResponseEntity.badRequest().body(r.getMessage());
			}
			
		}).orElseGet(() -> ResponseEntity.badRequest().body(Mensagem.LANCAMENTO_NAO_ENCONTRADO));

	}
	
	private Lancamento converterDtoToObj(LancamentoDTO dto) {
		
		Usuario usuario = usuarioService.buscarPeloId(dto.getUsuario())
				.orElseThrow(() -> new RegraNegocioException(Mensagem.USUARIO_NAO_EXISTE));
		
		Lancamento lancamento = new Lancamento();
		lancamento.setDescricao(dto.getDescricao());
		lancamento.setMes(dto.getMes());
		lancamento.setAno(dto.getAno());
		lancamento.setUsuario(usuario);
		lancamento.setValor(dto.getValor());
		
		if (!dto.getTipoLancamento().isBlank())
			lancamento.setTipoLancamento(TipoLancamento.valueOf(dto.getTipoLancamento().toUpperCase()));
		
		if (dto.getStatusLancamento() != null)
			lancamento.setStatusLancamento(StatusLancamento.valueOf(dto.getStatusLancamento().toUpperCase()));
				
		return lancamento;
	}
	
	
}
