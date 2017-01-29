package br.edu.ifma.csp.timetable.viewmodel;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;

import br.edu.ifma.csp.timetable.dao.TransacaoDao;
import br.edu.ifma.csp.timetable.model.Perfil;
import br.edu.ifma.csp.timetable.model.Transacao;
import br.edu.ifma.csp.timetable.repository.Transacoes;
import br.edu.ifma.csp.timetable.util.Lookup;

public class PerfilViewModel extends ViewModel<Perfil> {
	
	private Transacoes transacoes;
	
	private List<Transacao> colTransacoes;
	
	private List<Transacao> transacoesSelecionadas;
	
	@AfterCompose(superclass=true)
	@NotifyChange("colTransacoes")
	public void init(Component view) {
		
		transacoes = Lookup.dao(TransacaoDao.class);
		setColTransacoes(transacoes.all());
	}
	
	@Command
	@NotifyChange("entidadeSelecionada")
	public void adicionarTransacoes() {
		entidadeSelecionada.setTransacoes(transacoesSelecionadas);
	}
	
	@Command
	@NotifyChange("entidadeSelecionada")
	public void removerTransacoes() {
		
	}
	
	public List<Transacao> getColTransacoes() {
		return colTransacoes;
	}
	
	public void setColTransacoes(List<Transacao> colTransacoes) {
		this.colTransacoes = colTransacoes;
	}
	
	public List<Transacao> getTransacoesSelecionadas() {
		return transacoesSelecionadas;
	}
	
	public void setTransacoesSelecionadas(List<Transacao> transacoesSelecionadas) {
		this.transacoesSelecionadas = transacoesSelecionadas;
	}
}
