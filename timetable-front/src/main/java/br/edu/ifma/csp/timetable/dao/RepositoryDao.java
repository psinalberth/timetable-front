package br.edu.ifma.csp.timetable.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.repository.Repository;

public class RepositoryDao<T extends Entidade> implements Repository<T> {
	
	@Inject
	protected EntityManager manager;

	@Override
	public void save(T type) {
		this.manager.persist(type);
	}
	
	public T byId(int id) {
		return this.manager.find(retornaTipo(), id);
	}

	@Override
	public List<T> all() {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(retornaTipo());
		criteria.select(criteria.from(retornaTipo()));
		
		return manager.createQuery(criteria).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> retornaTipo() {
	    
		Class<?> clazz = this.getClass();
	     
	    while (!clazz.getSuperclass().equals(Entidade.class)) {
	        clazz = clazz.getSuperclass();
	    }
	     
	    ParameterizedType tipoGenerico = (ParameterizedType) clazz.getGenericSuperclass();
	    return (Class<T>) tipoGenerico.getActualTypeArguments()[0];
    }

	@Override
	public List<T> allBy() {
		return null;
	}

	@Override
	public void update(T type) {
		this.manager.merge(type);
	}

	@Override
	public void delete(T type) {
		this.manager.remove(type);
	}
}
