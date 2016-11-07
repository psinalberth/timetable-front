package br.edu.ifma.csp.timetable.viewmodel;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;

import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.repository.Repository;

public abstract class ViewModel<T extends Entidade> {
	
	protected Repository<T> repository;
	protected T entidadeSelecionada;
	
	private List<T> col;
	
	@AfterCompose
	@NotifyChange("*")
	public void initViewModel() throws NamingException, InstantiationException, IllegalAccessException {
		
		repository = InitialContext.doLookup("java:module/" + retornaTipo().getSimpleName() + "Dao");
		setCol(repository.all());
		novo();
	}
	
	public abstract void init(Component view);
	
	@Command
	@NotifyChange("*")
	public void novo() throws InstantiationException, IllegalAccessException {
		
		entidadeSelecionada = retornaTipo().newInstance();
		entidadeSelecionada.setUsuarioUltAlteracao("user");
		entidadeSelecionada.setDataUltAlteracao(new Date());
	}
	
	@Command
	@NotifyChange({"entidadeSelecionada", "removivel"})
	public void salvar() {
		repository.save(entidadeSelecionada);
	}
	
	@NotifyChange({"entidadeSelecionada", "editando", "consultando"})
	@Command
	public void pesquisar() {
		setEntidadeSelecionada(null);
	}
	
	@Command
	@NotifyChange("*")
	public void editar() {
		
	}
	
	@Command
	@NotifyChange("*")
	public void excluir() {
		
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> retornaTipo() {
	    
		Class<?> clazz = this.getClass();
	     
	    while (!clazz.getSuperclass().equals(ViewModel.class)) {
	        clazz = clazz.getSuperclass();
	    }
	     
	    ParameterizedType tipoGenerico = (ParameterizedType) clazz.getGenericSuperclass();
	    
	    return (Class<T>) tipoGenerico.getActualTypeArguments()[0];
    }
	
	public boolean isEditando() {
		return entidadeSelecionada != null;
	}
	
	public boolean isConsultando() {
		return !isEditando();
	}
	
	public boolean isRemovivel() {
		return !(isEditando() && entidadeSelecionada.getId() != 0);
	}
	
	public List<T> getCol() {
		return col;
	}
	
	public void setCol(List<T> col) {
		this.col = col;
	}
	
	public T getEntidadeSelecionada() {
		return entidadeSelecionada;
	}
	
	public void setEntidadeSelecionada(T entidadeSelecionada) {
		this.entidadeSelecionada = entidadeSelecionada;
	}
}
