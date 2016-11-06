package br.edu.ifma.csp.timetable.viewmodel.lookup;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;

import br.edu.ifma.csp.timetable.dao.ProfessorDao;
import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.Professor;
import br.edu.ifma.csp.timetable.repository.Professores;
import br.edu.ifma.csp.timetable.util.Lookup;

public class ProfessorLookupViewModel extends LookupViewModel<Professor> {
	
	private Professores professores;
	
	private Disciplina disciplina;
	private String nome;
	
	@AfterCompose(superclass=true)
	public void init(@BindingParam("disciplina") Disciplina disciplina) {
		this.disciplina = disciplina;
		lookup();
	}
	
	@NotifyChange("col")
	private void lookup() {
		
		if (getDisciplina() != null) {
			
			professores = Lookup.dao(ProfessorDao.class);
			setCol(professores.allByPreferenciaDisciplina(getDisciplina()));
		}
	}
	
	@Command
	@NotifyChange("col")
	public void pesquisar() {
		lookup();
	}
	
	@Command
	@NotifyChange("col")
	public void limpar() {
		lookup();
	}
	
	public Disciplina getDisciplina() {
		return disciplina;
	}
	
	@GlobalCommand
	@NotifyChange("*")
	public void setDisciplina(@BindingParam("disciplina") Disciplina disciplina) {
		this.disciplina = disciplina;
		pesquisar();
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
}
