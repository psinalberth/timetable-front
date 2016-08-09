package br.edu.ifma.csp.timetable.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.ifma.csp.timetable.model.Entidade;

public abstract class RepositoryDao<T extends Entidade> {
	
	@PersistenceContext(unitName="timetable-front")
	protected EntityManager manager;
	
	public void save(T type) {
		this.manager.merge(type);
	}
	
	public T by(String coluna, Object valor) {
		
		Class<T> clazz = retornaTipo();
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(clazz);
		Root<T> root = criteria.from(clazz);
		criteria.where(builder.equal(root.get(coluna), valor));
		
		try {
			
			return manager.createQuery(criteria).getSingleResult();
			
		} catch (NoResultException ex) {
			return null;
		}
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

	public List<T> allBy(String coluna, Object valor) {
		
		Class<T> clazz = retornaTipo();
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(clazz);
		Root<T> root = criteria.from(clazz);
		criteria.where(builder.equal(root.get(coluna), valor));
		
		return manager.createQuery(criteria).getResultList();
	}

	public void delete(T type) {
		this.manager.remove(byId(type.getId()));
	}
}
