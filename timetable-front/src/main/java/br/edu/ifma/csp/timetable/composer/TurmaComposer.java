package br.edu.ifma.csp.timetable.composer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Clients;

import br.edu.ifma.csp.timetable.model.DetalheDisciplina;
import br.edu.ifma.csp.timetable.model.Periodo;

public class TurmaComposer extends Composer<Periodo> {

	private static final long serialVersionUID = 7381784160763808819L;
	
	private List<Integer> colCodigos;

	@Init
	public void init() {
		
		colCodigos = new ArrayList<Integer>();
		colCodigos.add(1);
		colCodigos.add(2);
		colCodigos.add(3);
		colCodigos.add(4);
		colCodigos.add(20);
		
		Clients.resize(getBinder().getView());
		
		getBinder().notifyChange(this, "*");
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		initDetalhes();
	}
	
	public void initDetalhes() {
		
		entidade.setDetalhes(new HashSet<DetalheDisciplina>());
		
		DetalheDisciplina detalheSelecionado = new DetalheDisciplina();
		detalheSelecionado.setPeriodo(entidade);
		
		entidade.getDetalhes().add(detalheSelecionado);
		
		getBinder().notifyChange(this, "*");
	}
	
	public void adicionarDetalhe() {
		
		DetalheDisciplina detalheSelecionado = new DetalheDisciplina();
		detalheSelecionado.setPeriodo(entidade);
		
		entidade.getDetalhes().add(detalheSelecionado);		
		
		getBinder().notifyChange(entidade, "detalhes");
	}
	
	public void removerDetalhe() {
		
	}
	
	public List<Integer> getColCodigos() {
		return colCodigos;
	}
	
	public void setColCodigos(List<Integer> colCodigos) {
		this.colCodigos = colCodigos;
	}
}
