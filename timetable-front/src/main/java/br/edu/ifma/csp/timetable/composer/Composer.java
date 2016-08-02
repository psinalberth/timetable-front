package br.edu.ifma.csp.timetable.composer;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;

import org.zkoss.bind.BindComposer;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValuesException;
import org.zkoss.zk.ui.util.Clients;

import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.repository.Repository;
import br.edu.ifma.csp.timetable.util.Validations;

/**
 * Classe genérica de controle e interação com componentes visuais da aplicação.
 * 
 * @author inalberth
 *
 * @param <T> Modelo a ser abstraído.
 */
public abstract class Composer<T extends Entidade> extends BindComposer<Component>{

	private static final long serialVersionUID = 8906495139436799294L;
	
	protected Repository<T> repository;
	
	protected T entidade;
	
	private List<T> col;
	
	abstract void init();
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		repository = InitialContext.doLookup("java:module/" + retornaTipo().getSimpleName() + "Dao");
		
		setup();
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> retornaTipo() {
	    
		Class<?> clazz = this.getClass();
	     
	    while (!clazz.getSuperclass().equals(Composer.class)) {
	        clazz = clazz.getSuperclass();
	    }
	    
	    getBinder().notifyChange(this, "*");
	     
	    ParameterizedType tipoGenerico = (ParameterizedType) clazz.getGenericSuperclass();
	    return (Class<T>) tipoGenerico.getActualTypeArguments()[0];
    }
	
	public void setup() throws InstantiationException, IllegalAccessException {
		
		entidade = retornaTipo().newInstance();
		entidade.setUsuarioUltAlteracao("user");
		entidade.setDataUltAlteracao(new Date());
	}
	
	public void save() {
		
		Validations.validate(getBinder(), entidade);
		
		try {
			
			this.repository.save(entidade);
			
			Clients.showNotification("Informações salvas com sucesso.", "info", null, "middle_center", 2000);
			
			list();
			
		} catch (WrongValuesException ex) {	
			ex.printStackTrace();
		}
	}
	
	public void edit() {
	
	}
	
	public void list() {
		
		entidade = null;
		
		setCol(this.repository.all());
	}
	
	public boolean isEditando() {
		return entidade != null;
	}
	
	public boolean isConsultando() {
		return !isEditando();
	}
	
	public void delete() {
		this.repository.delete(entidade);
	}
	
	public T getEntidade() {
		return entidade;
	}
	
	@NotifyChange("*")
	public void setEntidade(T entidade) {
		this.entidade = entidade;
	}
	
	public List<T> getCol() {
		return col;
	}
	
	public void setCol(List<T> col) {
		this.col = col;
	}
}