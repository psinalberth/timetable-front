package br.edu.ifma.csp.timetable.composer;

import javax.inject.Inject;

import org.zkoss.bind.BindComposer;
import org.zkoss.zk.ui.Component;

import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.repository.Repository;

/**
 * Classe genérica de controle e interação com componentes visuais da aplicação.
 * 
 * @author inalberth
 *
 * @param <T> Modelo a ser abstraído.
 */
public abstract class Composer<T extends Entidade> extends BindComposer<Component>{

	private static final long serialVersionUID = 8906495139436799294L;
	
	@Inject
	private Repository<T> repository;
	private T type;
	
	abstract void init();
	
	public void save() {
		this.repository.save(type);
	}
	
	public void edit() {
		
	}
	
	public void show() {
		
	}
	
	public void delete() {
		this.repository.delete(type);
	}

}
