package br.edu.ifma.csp.timetable.viewmodel;

import java.util.Date;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValuesException;
import org.zkoss.zk.ui.util.Clients;

import br.edu.ifma.csp.timetable.dao.PerfilDao;
import br.edu.ifma.csp.timetable.model.Usuario;
import br.edu.ifma.csp.timetable.repository.Perfis;
import br.edu.ifma.csp.timetable.util.Lookup;
import br.edu.ifma.csp.timetable.util.Validations;

public class UsuarioViewModel extends ViewModel<Usuario> {
	
	private Perfis perfis;
	
	@AfterCompose(superclass=true)
	public void init(Component view) {
		perfis = Lookup.dao(PerfilDao.class);
	}
	
	@GlobalCommand
	@NotifyChange("entidadeSelecionada")
	public void novo() throws InstantiationException, IllegalAccessException {
		
		entidadeSelecionada = new Usuario();
		entidadeSelecionada.setUsuarioUltAlteracao("user");
		entidadeSelecionada.setDataUltAlteracao(new Date());
		entidadeSelecionada.setPerfil(perfis.by("nome", "Docente"));
	}
	
	@Command
	@NotifyChange("entidadeSelecionada")
	public void limpar() {
		entidadeSelecionada = null;
	}
	
	@Command
	@NotifyChange("entidadeSelecionada")
	public void salvar() throws WrongValuesException {
		
		try {
			
			Validations.validate(entidadeSelecionada, repository);
			
			repository.save(entidadeSelecionada);
			
			entidadeSelecionada = null;
			
			Clients.showNotification("Informações salvas com sucesso!", Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 1500);
			
		} catch(WrongValuesException ex) {
			Validations.showValidationErrors();
		}
	}
}
