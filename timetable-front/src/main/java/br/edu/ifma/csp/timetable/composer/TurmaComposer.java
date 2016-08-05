package br.edu.ifma.csp.timetable.composer;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Init;

import br.edu.ifma.csp.timetable.model.Turma;

public class TurmaComposer extends Composer<Turma>{

	private static final long serialVersionUID = 7381784160763808819L;
	
	private List<Integer> colCodigos;

	@Init
	@Override
	public void init() {
		
		colCodigos = new ArrayList<Integer>();
		colCodigos.add(1);
		colCodigos.add(2);
		colCodigos.add(3);
		colCodigos.add(4);
		colCodigos.add(20);
	}
	
	public List<Integer> getColCodigos() {
		return colCodigos;
	}
	
	public void setColCodigos(List<Integer> colCodigos) {
		this.colCodigos = colCodigos;
	}
}
