package br.edu.ifma.csp.timetable.composer.lookup;

import org.zkoss.bind.annotation.Init;

import br.edu.ifma.csp.timetable.dao.MatrizCurricularDao;
import br.edu.ifma.csp.timetable.model.Curso;
import br.edu.ifma.csp.timetable.model.MatrizCurricular;
import br.edu.ifma.csp.timetable.repository.MatrizesCurriculares;
import br.edu.ifma.csp.timetable.util.Lookup;

public class MatrizCurricularLookupComposer extends LookupComposer<MatrizCurricular> {
	
	private static final long serialVersionUID = 3618403743185526095L;
	
	private MatrizesCurriculares matrizes;
	
	private Integer ano;
	private Curso curso;
	
	@Init
	public void init() {
		
		matrizes = Lookup.dao(MatrizCurricularDao.class);
		setCol(matrizes.all());
		getBinder().notifyChange(this, "*");
	}
	
	public void search() {
		
	}
	
	public Integer getAno() {
		return ano;
	}
	
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	public Curso getCurso() {
		return curso;
	}
	
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
}