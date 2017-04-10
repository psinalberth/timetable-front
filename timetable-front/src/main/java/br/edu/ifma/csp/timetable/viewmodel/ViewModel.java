package br.edu.ifma.csp.timetable.viewmodel;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValuesException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import br.edu.ifma.csp.timetable.model.Entidade;
import br.edu.ifma.csp.timetable.model.Usuario;
import br.edu.ifma.csp.timetable.repository.Repository;
import br.edu.ifma.csp.timetable.util.Validations;

/**
 * Classe destinada a gerenciar os comportamentos na camada de visão através da interação com usuário e invocar as operações correspondentes nas
 * camadas de modelo e dados (CRUD). A arquitetura de programação utilizada é MVVM (<i>Model-View-ViewModel</i>), a qual possui, entre outras 
 * características a não utilização de referências para qualquer componente gráfico, atualizando as propriedades do objeto selecionado 
 * a partir das notificações do <i>binder</i>, por fim atualizando os componentes que encapsulam as propriedades modificadas.   
 * 
 * @see Command
 * @see NotifyChange
 * 
 * @author inalberth
 *
 * @param <T> Classe de modelo abstraído.
 */
public abstract class ViewModel<T extends Entidade> {
	
	protected Repository<T> repository;
	protected T entidadeSelecionada;
	
	private List<T> col;
	
	@AfterCompose
	@NotifyChange("*")
	public void initViewModel() throws NamingException, InstantiationException, IllegalAccessException {
		
		repository = InitialContext.doLookup("java:module/" + retornaTipo().getSimpleName() + "Dao");
		setCol(repository.all());
	}
	
	public abstract void init(Component view);
	
	/**
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	
	@Command
	@NotifyChange({"entidadeSelecionada", "consultando", "removivel", "editando", "entidadeFiltro"})
	public void novo() throws InstantiationException, IllegalAccessException {
		
		Usuario usuario = (Usuario) Executions.getCurrent().getSession().getAttribute("usuario") ;
		
		entidadeSelecionada = retornaTipo().newInstance();
		entidadeSelecionada.setUsuarioUltAlteracao(usuario.getLogin());
		entidadeSelecionada.setDataUltAlteracao(new Date());
	}
	
	@Command
	public void salvar() throws WrongValuesException {
		
		try {
			
			Validations.validate(entidadeSelecionada, repository);
			
			repository.save(entidadeSelecionada);
			
			BindUtils.postNotifyChange(null, null, this, "entidadeSelecionada");
			BindUtils.postNotifyChange(null, null, this, "consultando");
			BindUtils.postNotifyChange(null, null, this, "removivel");
			BindUtils.postNotifyChange(null, null, this, "editando");
			BindUtils.postNotifyChange(null, null, this, "col");
			
			pesquisar();
			
			Clients.showNotification("Informações salvas com sucesso!", Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 1500);
			
		} catch(WrongValuesException ex) {
			Validations.showValidationErrors();
		}
	}
	
	@Command
	@GlobalCommand
	@NotifyChange("*")
	public void pesquisar() {
	
		setEntidadeSelecionada(null);
		setCol(repository.all());
	}
	
	@Command
	@NotifyChange({"entidadeFiltro", "col"})
	public void filtrar() {
		
	}
	
	@Command
	@NotifyChange({"entidadeSelecionada", "consultando", "removivel", "editando"})
	public void editar() {
		
	}
	
	@Command
	@NotifyChange({"entidadeSelecionada", "consultando", "removivel", "editando", "col"})
	public void excluir() {
		
		Messagebox.show("Deseja realmente excluir este registro?", "Excluir Registro?", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
			
			@NotifyChange({"entidadeSelecionada", "consultando", "removivel", "editando", "col"})
			public void onEvent(Event ev) throws Exception {
				
				if (ev.getName().equals(Messagebox.ON_YES)) {
					
					repository.delete(entidadeSelecionada);

					Clients.showNotification("Registro excluído com sucesso!", Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 1500);
					
					BindUtils.postGlobalCommand(null, null, "pesquisar", null);
				}
			}
		});
	}
	
	/**
	 * Método utilizado para recuperar, por reflexão, a classe de modelo abstraído. 
	 * Utilizado para criar uma nova instância de determinado objeto.
	 * 
	 * @return Referência de classe de modelo.
	 * 
	 * @see ViewModel #novo()
	 */
	
	@SuppressWarnings("unchecked")
	private Class<T> retornaTipo() {
	    
		Class<?> clazz = this.getClass();
	     
	    while (!clazz.getSuperclass().equals(ViewModel.class)) {
	        clazz = clazz.getSuperclass();
	    }
	     
	    ParameterizedType tipoGenerico = (ParameterizedType) clazz.getGenericSuperclass();
	    
	    return (Class<T>) tipoGenerico.getActualTypeArguments()[0];
    }
	
	/**
	 * Checa se o objeto selecionando pode ser removível (apenas para gerenciar comportamento do botão 'Excluir').
	 * 
	 * @return Retorna {@code true} se o elemento pode ser removido (está salvo) ou {@code false} caso não seja possível removê-lo.
	 */
	public boolean isRemovivel() {
		return entidadeSelecionada == null || entidadeSelecionada.getId() == 0;
	}
	
	/**
	 * Checa se o usuário está editando um objeto (apenas para gerenciar comportamento do formulário).
	 * 
	 * @return Retorna {@code true} se um objeto estiver sendo criado ou editado ou {@code false} em caso contrário.
	 */
	public boolean isEditando() {
		return entidadeSelecionada != null;
	}
	
	/**
	 * Checa se o usuário está visualizando uma lista de objetos de determinado tipo (apenas para gerenciar comportamento da listagem de objetos).
	 * 
	 * @return Retorna {@code true} se uma lista de objetos estiver sendo visualizada ou {@code false} em caso contrário.
	 */
	public boolean isConsultando() {
		return !isEditando();
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
