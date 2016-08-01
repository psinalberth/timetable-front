package br.edu.ifma.csp.timetable.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import br.edu.ifma.csp.timetable.model.Entidade;

public abstract class RepositoryDao<T extends Entidade> {
	
	@PersistenceContext(unitName="timetable-front")
	protected EntityManager manager;
	
	public void save(T type) {
		this.manager.persist(type);
	}
	
	public T byId(int id) {
		return this.manager.find(retornaTipo(), id);
	}

	public List<T> all() {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(retornaTipo());
		criteria.select(criteria.from(retornaTipo()));
		
		return manager.createQuery(criteria).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> retornaTipo() {
	    
		Class<?> clazz = this.getClass();
	     
	    while (!clazz.getSuperclass().equals(RepositoryDao.class)) {
	        clazz = clazz.getSuperclass();
	    }
	     
	    ParameterizedType tipoGenerico = (ParameterizedType) clazz.getGenericSuperclass();
	    return (Class<T>) tipoGenerico.getActualTypeArguments()[0];
    }

	public List<T> allBy() {
		return null;
	}

	public void update(T type) {
		this.manager.merge(type);
	}

	public void delete(T type) {
		this.manager.remove(type);
	}
}
