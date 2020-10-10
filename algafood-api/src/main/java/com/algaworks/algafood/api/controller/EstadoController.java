package com.algaworks.algafood.api.controller;

import java.awt.PageAttributes.MediaType;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(value = "/estados", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE) //este "produces pode ser no GetMapping tb para que só seja retornado valores "JSON", outra opção é XML
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroestado;
	
	@GetMapping
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}
	
	@GetMapping("/{estadoId}")
	public ResponseEntity<Estado> buscar(@PathVariable Long estadoId){
		Optional<Estado> estado = estadoRepository.findById(estadoId);
		
		if(estado.isPresent()) {
			return ResponseEntity.ok(estado.get());
		}
		return ResponseEntity.notFound().build();		
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar (@RequestBody Estado estado){
		try {
			estado = cadastroestado.salvar(estado);		
			return ResponseEntity.status(HttpStatus.CREATED).body(estado);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@PutMapping("/{estadoId}")
	public ResponseEntity<?> atualizar (@PathVariable Long estadoId, @RequestBody Estado estado){
		
		try {
			Optional<Estado> estadoAtual = estadoRepository.findById(estadoId);
			
			if(estadoAtual.isPresent()) {
				BeanUtils.copyProperties(estado, estadoAtual.get(), "id");
				
				Estado estadoSalvo = cadastroestado.salvar(estadoAtual.get());
				return ResponseEntity.ok(estadoSalvo);
				}
				return ResponseEntity.notFound().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}
	@DeleteMapping("/{estadoId}")
	public ResponseEntity<?> remover (@PathVariable Long estadoId){
		try {
			cadastroestado.excluir(estadoId);
			return ResponseEntity.noContent().build();
			
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		}catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

}
