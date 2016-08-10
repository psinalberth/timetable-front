package br.edu.ifma.csp.timetable.composer;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;

import org.zkoss.bind.BindComposer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValuesException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

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
		setCol(repository.all());
		setup();
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
	
	public void setup() throws InstantiationException, IllegalAccessException {
		
		Clients.clearWrongValue(getBinder().getView());
		
		entidade = retornaTipo().newInstance();
		entidade.setUsuarioUltAlteracao("user");
		entidade.setDataUltAlteracao(new Date());
		
		getBinder().notifyChange(this, "*");
	}
	
	@SuppressWarnings("unchecked")
	public void save() {
		
		getBinder().notifyChange(this, "*");
		
		Validations.validate(getBinder(), entidade, (Repository<Entidade>) repository);
		
		try {
			
			this.repository.save(entidade);
			
			Clients.showNotification("Informações salvas com sucesso.", "info", null, "middle_center", 1000);
			
			list();
			
		} catch (WrongValuesException ex) {	
			ex.printStackTrace();
		}
	}
	
	public void edit() {		
		getBinder().notifyChange(this, "*");
	}
	
	public void list() {
		
		Clients.clearWrongValue(getBinder().getView());
		
		entidade = null;
		setCol(this.repository.all());
		
		getBinder().notifyChange(this, "*");
	}
	
	public boolean isEditando() {
		return entidade != null;
	}
	
	public boolean isConsultando() {
		return !isEditando();
	}
	
	public boolean isRemovivel() {
		return !(isEditando() && entidade.getId() != 0);
	}
	
	public void delete() {
		
		Messagebox.show("Deseja realmente excluir o registro selecionado?", "Excluir Registro?", (Messagebox.YES | Messagebox.NO), Messagebox.QUESTION, new EventListener<Event>() {

			@Override
			public void onEvent(Event ev) throws Exception {
				if (ev.getName().equals(Messagebox.ON_YES)) {
					
					repository.delete(entidade);
					
					Clients.showNotification("Registro excluído com sucesso.", "info", null, "middle_center", 1000);
					
					list();
				} 
				
			}
		});
		
		
	}
	
	public T getEntidade() {
		return entidade;
	}
	
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