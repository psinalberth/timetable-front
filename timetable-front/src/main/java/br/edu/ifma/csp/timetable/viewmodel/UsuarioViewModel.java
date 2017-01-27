package br.edu.ifma.csp.timetable.viewmodel;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;

import br.edu.ifma.csp.timetable.model.Usuario;

public class UsuarioViewModel extends ViewModel<Usuario> {

	@AfterCompose(superclass=true)
	public void init(Component view) {
		
	}
	
	@Command
	@NotifyChange("entidadeSelecionada")
	public void limpar() {
		entidadeSelecionada = null;
	}
}
