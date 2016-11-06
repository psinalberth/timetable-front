package br.edu.ifma.csp.timetable.viewmodel;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.repository.Repository;

public abstract class ViewModel<T extends Entidade> {
	
	protected Repository<T> repository;
	protected T entidadeSelecionada;
	
	private Boolean editando = false;
	private Boolean removivel = false;
	private Boolean consultando = false;
	
	private List<T> col;
	
	@AfterCompose
	@NotifyChange("*")
	public void initViewModel() throws NamingException, InstantiationException, IllegalAccessException {
		
		repository = InitialContext.doLookup("java:module/" + retornaTipo().getSimpleName() + "Dao");
		setCol(repository.all());
		novo();
	}
	
	@Command
	@NotifyChange({"entidadeSelecionada", "editando", "consultando"})
	public void novo() throws InstantiationException, IllegalAccessException {
		
		entidadeSelecionada = retornaTipo().newInstance();
		entidadeSelecionada.setUsuarioUltAlteracao("user");
		entidadeSelecionada.setDataUltAlteracao(new Date());
		
		setEditando(true);
		setConsultando(false);
	}
	
	@Command
	public void salvar() {
		
	}
	
	@NotifyChange({"entidadeSelecionada", "editando", "consultando"})
	@Command
	public void pesquisar() {
		
		setEditando(false);
		setEntidadeSelecionada(null);
		setConsultando(true);
	}
	
	@Command
	@NotifyChange("*")
	public void editar() {
		
		setEditando(true);
		setConsultando(false);
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
	
	public Boolean getEditando() {
		return editando;
	}
	
	public void setEditando(Boolean editando) {
		this.editando = editando;
	}
	
	public Boolean getConsultando() {
		return consultando;
	}
	
	public void setConsultando(Boolean consultando) {
		this.consultando = consultando;
	}
	
	public Boolean getRemovivel() {
		return removivel;
	}
	
	public void setRemovivel(Boolean removivel) {
		this.removivel = removivel;
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
