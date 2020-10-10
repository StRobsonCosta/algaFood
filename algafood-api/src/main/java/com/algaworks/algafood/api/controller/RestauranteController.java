package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sun.java.util.jar.pack.Package.Class.Field;

@RestController
@RequestMapping (value = "/restaurantes" )
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@GetMapping
	public List<Restaurante> listar() {        //criei método listar
		return restauranteRepository.findAll(); // e aqui retorna o método listar lá do rptory... mas lá o metodo chama-se todos() 

	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId){
		Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
		
		if(restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	/*@PostMapping	
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante  adicionar (@RequestBody Restaurante restaurante) {
		return cadastroRestaurante.salvar(restaurante);*/
	
	@PostMapping
	public ResponseEntity<?>  adicionar (@RequestBody Restaurante restaurante) {
		  try {
			Restaurante restauranteAtual = cadastroRestaurante.salvar(restaurante);
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(restauranteAtual);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
			
		}
		
	}
	
	@PutMapping ("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante){

		try {
			Optional <Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
			
			if(restauranteAtual.isPresent()) {
			BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id");
			
			Restaurante restauranteSalvo = cadastroRestaurante.salvar(restauranteAtual.get());
			return ResponseEntity.ok(restauranteSalvo);
			}
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}		
		
	}
	
	
	 	@DeleteMapping("/{restauranteId}")
	    public ResponseEntity<Restaurante> remover (@PathVariable Long restauranteId){
		
		try {
			cadastroRestaurante.excluir(restauranteId);
			return ResponseEntity.noContent().build();
			
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	 
	 }
	 	@PatchMapping("/{restauranteId}")
	 	public ResponseEntity<?>atualizarParcial(@PathVariable Long restauranteId,
	 			@RequestBody Map<String, Object> campos){
	 		Restaurante restauranteAtual = restauranteRepository.findById(restauranteId).orElse(null);
	 		
	 		//if(restauranteAtual.isEmpty()) {
	 			//return ResponseEntity.notFound().build();
	 		//}
	 		merge(campos,restauranteAtual);
	 		
	 		return atualizar(restauranteId, restauranteAtual);
	 	}

		private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
			ObjectMapper objectMapper = new ObjectMapper();
			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
			
			dadosOrigem.forEach((nomePropriedade, valorPropriedade) ->{
			 	 Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			 	 field.setAccessible(true);
			 	 
			 	 Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
								
	 			//System.out.println(nomePropriedade+ "=" + valorPropriedade);
	 			 
	 			  ReflectionUtils.setField(field, restauranteDestino, novoValor);
	 			 
	 		});
		}
	 	

}
