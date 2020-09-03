package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping (value = "/restaurantes" , produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@GetMapping
	public List<Restaurante> listar() {        //criei método listar
		return restauranteRepository.todos(); // e aqui retorna o método listar lá do rptory... mas lá o metodo chama-se todos() 
	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId){
		Restaurante restaurante = restauranteRepository.buscar(restauranteId);
		
		if(restaurante != null) {
			return ResponseEntity.ok(restaurante);
		}
		return ResponseEntity.notFound().build();
	}

}
