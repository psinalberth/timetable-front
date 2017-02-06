package br.edu.ifma.csp.timetable.viewmodel;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import br.edu.ifma.csp.timetable.dao.UsuarioDao;
import br.edu.ifma.csp.timetable.model.Transacao;
import br.edu.ifma.csp.timetable.model.Usuario;
import br.edu.ifma.csp.timetable.repository.Usuarios;
import br.edu.ifma.csp.timetable.util.Lookup;

public class NavigationViewModel {
	
	Usuarios usuarios;
	
	private String fragment;
	private List<Transacao> transacoes;
	
	@Init
	@Command
	@NotifyChange({"transacoes", "fragment"})
	public void init() {
		
		usuarios = Lookup.dao(UsuarioDao.class);
		
		Usuario usuario = (Usuario) Executions.getCurrent().getSession().getAttribute("usuario");
		
		if (usuario != null) {
			setTransacoes(usuario.getPerfil().getTransacoes());
		}
		
		setFragment("../partials/zul/welcome.zul");
	}
	
	@GlobalCommand("irPara")
	@NotifyChange("fragment")
	public void irPara(@BindingParam("transacao") String transacao) {
		
		if (transacao != null) {
			setFragment(transacao);
		}
	}
	
	public String getFragment() {
		return fragment;
	}
	
	public void setFragment(String fragment) {
		this.fragment = fragment;
	}
	
	public List<Transacao> getTransacoes() {
		return transacoes;
	}
	
	public void setTransacoes(List<Transacao> transacoes) {
		this.transacoes = transacoes;
	}
}
