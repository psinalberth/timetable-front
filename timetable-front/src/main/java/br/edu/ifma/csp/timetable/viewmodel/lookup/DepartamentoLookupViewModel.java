package br.edu.ifma.csp.timetable.viewmodel.lookup;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;

import br.edu.ifma.csp.timetable.model.Departamento;

public class DepartamentoLookupViewModel extends LookupViewModel<Departamento> {
	
	private String codigo;
	private String nome;
	
	@AfterCompose(superclass=true)
	public void init() {
		
	}

	@Command
	public void pesquisar() {
		
	}

	@Command
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
