package br.edu.ifma.csp.timetable.composer.lookup;

import org.zkoss.bind.annotation.Init;

import br.edu.ifma.csp.timetable.dao.DepartamentoDao;
import br.edu.ifma.csp.timetable.model.Departamento;
import br.edu.ifma.csp.timetable.repository.Departamentos;
import br.edu.ifma.csp.timetable.util.Lookup;

public class DepartamentoLookupComposer extends LookupComposer<Departamento> {
	
	private static final long serialVersionUID = 7856044171588957119L;
	
	private Departamentos departamentos;
	
	private String codigo;
	private String nome;
	
	@Init
	public void init() {
		
		departamentos = Lookup.dao(DepartamentoDao.class);
		
		search();
	}
	
	public void search() {
		
		setCol(departamentos.allByCodigoOrDescricao(codigo, nome));
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
