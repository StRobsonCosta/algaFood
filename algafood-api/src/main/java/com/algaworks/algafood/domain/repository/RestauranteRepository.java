package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
	
	/*List<Restaurante> listar();
	Restaurante buscar (Long id);
	Restaurante salvar (Restaurante restaurante);
	void remover (Long id);*/

}
