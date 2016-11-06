package br.edu.ifma.csp.timetable.viewmodel.lookup;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.NotifyChange;

import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.repository.Repository;

public abstract class LookupViewModel<T extends Entidade> {
	
	protected Repository<T> repository;
	private List<T> col;
	private T entidadeSelecionada;
	
	@AfterCompose
	@NotifyChange("*")
	public void initLookup() throws NamingException {
		repository = InitialContext.doLookup("java:module/" + retornaTipo().getSimpleName() + "Dao");
		setCol(repository.all());
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> retornaTipo() {
	    
		Class<?> clazz = this.getClass();
	     
	    while (!clazz.getSuperclass().equals(LookupViewModel.class)) {
	        clazz = clazz.getSuperclass();
	    }
	     
	    ParameterizedType tipoGenerico = (ParameterizedType) clazz.getGenericSuperclass();
	    
	    return (Class<T>) tipoGenerico.getActualTypeArguments()[0];
    }
	
	public void pesquisar() {
		
	}
	
	public void limpar() {
		
	}
	
	public T getEntidadeSelecionada() {
		return entidadeSelecionada;
	}
	
	public void setEntidadeSelecionada(T entidadeSelecionada) {
		this.entidadeSelecionada = entidadeSelecionada;
	}
	
	public List<T> getCol() {
		return col;
	}
	
	public void setCol(List<T> col) {
		this.col = col;
	}
}
