package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.CozinhasXmlWrapper;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

import lombok.Delegate;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@GetMapping
	public List<Cozinha> listar(){
		return cozinhaRepository.findAll();
		
	}
	
	/*@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)       // método deletado no curso... só pra ser visto em XML no Postman
	public CozinhasXmlWrapper listarXml(){
		return new CozinhasXmlWrapper(cozinhaRepository.listar());
	}*/
	

	
	//public Cozinha buscar (@PathVariable Long cozinhaId) {    //outra forma possivel(@PathVariable ("cozinhaId") Long id) {
		//return cozinhaRepository.buscar(cozinhaId);
	@GetMapping("/{cozinhaId}")
	public  ResponseEntity<Cozinha> buscar (@PathVariable Long cozinhaId){
		Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
		
		if (cozinha.isPresent()) {
			return ResponseEntity.ok(cozinha.get());
		}
		
		return ResponseEntity.notFound().build(); //return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
		/*** Outras opções para Status:
		 * 		return ResponseEntity.status(HttpStatus.OK).body(cozinha); //ou: return ResponseEntity.ok(cozinha);
		 * ou:
		 *      HttpHeaders headers = new HttpHeaders();
		 *      headers.add(HttpHeaders.LOCATION, "http://api.algafood.local:8080/cozinhas");
		 *      
		 *      return ResponseEntity
		 *      .status(HttpStatus.FOUND)
		 *      .headers(headers)
		 *      .build(); 
		 */
				
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar (@RequestBody Cozinha cozinha) {
		return cadastroCozinha.salvar(cozinha);
		
	}
	
	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar (@PathVariable Long cozinhaId,
			@RequestBody Cozinha cozinha){
		Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);
		
		if(cozinhaAtual.isPresent()) {
			//cozinhaAtual.setNome(cozinha.getNome());
			BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
			
			Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinhaAtual.get());
			return ResponseEntity.ok(cozinhaSalva);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<?> remover (@PathVariable Long cozinhaId){
		
		try {
			cadastroCozinha.excluir(cozinhaId);
			return ResponseEntity.noContent().build();
			
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(e.getMessage());
		}
		
		/*Antes ...
		 * Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
		  try {
			if (cozinha != null) {
				cozinhaRepository.remover(cozinha);
				return ResponseEntity.noContent().build();
				
			}
			return ResponseEntity.notFound().build();
			
		}catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}*/

	}
}
