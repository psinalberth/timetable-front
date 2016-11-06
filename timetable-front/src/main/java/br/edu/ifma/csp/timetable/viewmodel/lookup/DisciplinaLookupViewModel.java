package br.edu.ifma.csp.timetable.viewmodel.lookup;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;

import br.edu.ifma.csp.timetable.dao.DisciplinaDao;
import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.MatrizCurricular;
import br.edu.ifma.csp.timetable.repository.Disciplinas;
import br.edu.ifma.csp.timetable.util.Lookup;

public class DisciplinaLookupViewModel extends LookupViewModel<Disciplina> {
	
	private Disciplinas disciplinas;
	
	private MatrizCurricular matrizCurricular;
	private Integer codigo;
	private String sigla;
	private String descricao;
	
	@AfterCompose(superclass=true)
	public void init(@BindingParam("matrizCurricular") MatrizCurricular matrizCurricular) {
		this.matrizCurricular = matrizCurricular;
		build();
	}
	
	@NotifyChange("col")
	private void build() {
		
		if (matrizCurricular != null) {
			
			disciplinas = Lookup.dao(DisciplinaDao.class);
			setCol(disciplinas.allByMatrizCurricular(matrizCurricular));
		}
	}
	
	@Command
	public void pesquisar() {
		build();
	}
	
	@Command
	public void limpar() {
	
	}
	
	public MatrizCurricular getMatrizCurricular() {
		return matrizCurricular;
	}
	
	@GlobalCommand
	@NotifyChange("matrizCurricular")
	public void setMatrizCurricular(@BindingParam("matrizCurricular") MatrizCurricular matrizCurricular) {
		this.matrizCurricular = matrizCurricular;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
