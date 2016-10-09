package br.edu.ifma.csp.timetable.composer.lookup;

import org.zkoss.bind.annotation.Init;

import br.edu.ifma.csp.timetable.dao.CursoDao;
import br.edu.ifma.csp.timetable.model.Curso;
import br.edu.ifma.csp.timetable.repository.Cursos;
import br.edu.ifma.csp.timetable.util.Lookup;

public class CursoLookupComposer extends LookupComposer<Curso> {

	private static final long serialVersionUID = -5971563460064982966L;
	
	private Cursos cursos;
	
	private String codigo;
	private String nome;
	
	@Init
	public void init() {
		cursos = Lookup.dao(CursoDao.class);
		
		search();
	}
	
	public void search() {
		
		setCol(cursos.allByCodigoOrDescricao(codigo, nome));
		getBinder().notifyChange(this, "*");
	}
	
	public void clear() {
		
		setCodigo(null);
		setNome(null);
		
		search();
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
}
