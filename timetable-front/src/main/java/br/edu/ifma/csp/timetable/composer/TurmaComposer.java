package br.edu.ifma.csp.timetable.composer;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Init;

import br.edu.ifma.csp.timetable.dao.DisciplinaDao;
import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.Turma;
import br.edu.ifma.csp.timetable.repository.Disciplinas;
import br.edu.ifma.csp.timetable.util.Lookup;

public class TurmaComposer extends Composer<Turma>{

	private static final long serialVersionUID = 7381784160763808819L;
	
	private List<Integer> colCodigos;
	private List<Disciplina> colDisciplinas;

	@Init
	public void init() {
		
		Disciplinas disciplinas = Lookup.dao(DisciplinaDao.class);
		setColDisciplinas(disciplinas.all());
		
		colCodigos = new ArrayList<Integer>();
		colCodigos.add(1);
		colCodigos.add(2);
		colCodigos.add(3);
		colCodigos.add(4);
		colCodigos.add(20);
		
		getBinder().notifyChange(this, "*");
	}
	
	public List<Integer> getColCodigos() {
		return colCodigos;
	}
	
	public void setColCodigos(List<Integer> colCodigos) {
		this.colCodigos = colCodigos;
	}
	
	public List<Disciplina> getColDisciplinas() {
		return colDisciplinas;
	}
	
	public void setColDisciplinas(List<Disciplina> colDisciplinas) {
		this.colDisciplinas = colDisciplinas;
	}
}
