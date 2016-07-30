package br.edu.ifma.csp.timetable.composer;

import java.lang.reflect.ParameterizedType;

import org.zkoss.bind.BindComposer;
import org.zkoss.zk.ui.Component;

import br.edu.ifma.csp.timetable.dao.RepositoryDao;
import br.edu.ifma.csp.timetable.model.Entidade;

/**
 * Classe genérica de controle e interação com componentes visuais da aplicação.
 * 
 * @author inalberth
 *
 * @param <T> Modelo a ser abstraído.
 */
public abstract class Composer<T extends Entidade> extends BindComposer<Component>{

	private static final long serialVersionUID = 8906495139436799294L;
	
	private RepositoryDao<T> repository;
	protected T entidade;
	
	abstract void init();
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		entidade = retornaTipo().newInstance();
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> retornaTipo() {
	    
		Class<?> clazz = this.getClass();
	     
	    while (!clazz.getSuperclass().equals(Composer.class)) {
	        clazz = clazz.getSuperclass();
	    }
	     
	    ParameterizedType tipoGenerico = (ParameterizedType) clazz.getGenericSuperclass();
	    return (Class<T>) tipoGenerico.getActualTypeArguments()[0];
    }
	
	public void save() {
		this.repository.save(entidade);
	}
	
	public void edit() {
		
	}
	
	public void show() {
		
	}
	
	public void delete() {
		this.repository.delete(entidade);
	}
	
	public T getEntidade() {
		return entidade;
	}
	
	public void setEntidade(T entidade) {
		this.entidade = entidade;
	}
}