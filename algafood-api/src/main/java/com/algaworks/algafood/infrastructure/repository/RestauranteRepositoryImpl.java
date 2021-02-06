/*package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository{
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Restaurante> listar() {
		return manager.createQuery("from Restaurante", Restaurante.class)
				.getResultList();
	}

	@Override
	public Restaurante buscar(Long id) {
		
		return manager.find(Restaurante.class, id);
	}

	@Transactional
	@Override
	public Restaurante salvar(Restaurante restaurante) {
		
		return manager.merge(restaurante);
	}

	@Transactional
	@Override
	public void remover(Long id) {
		
		Restaurante restaurante = buscar(id);
		
		if(restaurante == null) {
			throw new EmptyResultDataAccessException(1);
		}
		manager.remove(restaurante);
		
	}
	
	

}
*/

package com.algaworks.algafood.infrastructure.repository;

import static com.algaworks.algafood.infrastructure.repository.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafood.infrastructure.repository.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;
	
	@Override
	public List<Restaurante> find (String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
		
		var builder = manager.getCriteriaBuilder();
		var criteria = builder.createQuery(Restaurante.class);
		var root = criteria.from(Restaurante.class);
		var predicates = new ArrayList<Predicate>();
		
		if(StringUtils.hasText(nome)) {
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
		}
		if(taxaFreteInicial != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
		}
		if(taxaFreteInicial != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
		}
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		var query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
	
	@Override
	public List<Restaurante> findComFreteGratis(String nome){
		return restauranteRepository.findAll(comFreteGratis()
						.and(comNomeSemelhante(nome)));
	}
	
}