package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository 
				extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, 
				JpaSpecificationExecutor<Restaurante> {
	
	List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	//@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
		//List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha);
	
	//List<Restaurante> findByNomeContainingAndNomeContaining (String nome);
	
	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
	
	List<Restaurante> findTop2ByNomeContaining(String nome);
	
	int countByCozinhaId(Long cozinha);
	
	/*List<Restaurante> listar();
	Restaurante buscar (Long id);
	Restaurante salvar (Restaurante restaurante);
	void remover (Long id);*/

}
