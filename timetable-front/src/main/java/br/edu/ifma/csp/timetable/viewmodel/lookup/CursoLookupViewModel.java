package br.edu.ifma.csp.timetable.viewmodel.lookup;

import org.zkoss.bind.annotation.AfterCompose;

import br.edu.ifma.csp.timetable.model.Curso;

public class CursoLookupViewModel extends LookupViewModel<Curso> {
	
	private String codigo;
	private String nome;
	
	@AfterCompose(superclass=true)
	public void init() {
		
	}
	
	public void pesquisar() {
		
	}

	public void limpar() {
		
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
