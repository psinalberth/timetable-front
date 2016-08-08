package br.edu.ifma.csp.timetable.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.edu.ifma.csp.timetable.model.Entidade;

public abstract class RepositoryDao<T extends Entidade> {
	
	@PersistenceContext(unitName="timetable-front")
	protected EntityManager manager;
	
	@PersistenceContext
	protected Session session;
	
	public void save(T type) {
		
//		this.manager.merge(type);
		this.session.merge(type);
	}
	
	public int countByCodigo(int id, String codigo) {
		
		Class<T> clazz = retornaTipo();
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(clazz);
		Root<T> root = criteria.from(clazz);
		
		criteria.where(builder.equal(root.get("codigo"), codigo));
		criteria.where(builder.equal(root.get("id"), id));
		
		return manager.createQuery(criteria).getResultList().size();
	}
	
	public T by(String coluna, Object valor) {
		
		Criteria criteria = this.session.createCriteria(retornaTipo());
		criteria.add(Restrictions.eq(coluna, valor));
		
		if (criteria.uniqueResult() == null)
			return null;
		
		return (T) criteria.uniqueResult();
		
		
		/*List<T> result = allBy(coluna, valor);
		
		if (result.size() > 0)
			return result.get(0);
		
		return null;*/
//		Class<T> clazz = retornaTipo();
//		
//		CriteriaBuilder builder = manager.getCriteriaBuilder();
//		CriteriaQuery<T> criteria = builder.createQuery(clazz);
//		Root<T> root = criteria.from(clazz);
//		criteria.where(builder.equal(root.get(coluna), valor));
//
//		try {
//			
//			return manager.createQuery(criteria).getSingleResult();
//			
//		} catch (Exception ex) {
//			try {
//				
//				T result = clazz.newInstance();
//				result.setId(-1);
//				
//				return result;
//			} catch (InstantiationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		return null;
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
	
	public Session getSession() {
		return session;
	}
}
