package br.edu.ifma.csp.timetable.composer.lookup;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Init;

import br.edu.ifma.csp.timetable.dao.ProfessorDao;
import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.Professor;
import br.edu.ifma.csp.timetable.repository.Professores;
import br.edu.ifma.csp.timetable.util.Lookup;

public class ProfessorPreferenciaLookupComposer extends LookupComposer<Professor> {
		
	private static final long serialVersionUID = -5417890253541546669L;
	
	private Professores professores;
	
	private String nome;
	
	private Disciplina disciplina;
	
	@Init
	public void init(@BindingParam("disciplina") Disciplina disciplina) {
		
		this.disciplina = disciplina;
		professores = Lookup.dao(ProfessorDao.class);
		setCol(professores.all());
		getBinder().notifyChange(this, "*");
	}
	
	public void search() {
		getBinder().notifyChange(this, "*");
	}
	
	public void clear() {
		
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Disciplina getDisciplina() {
		return disciplina;
	}
	
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
}
