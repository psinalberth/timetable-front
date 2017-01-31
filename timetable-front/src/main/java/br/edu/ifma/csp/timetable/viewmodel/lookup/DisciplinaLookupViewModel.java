package br.edu.ifma.csp.timetable.viewmodel.lookup;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.Pair;

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
	private boolean eletiva;
	
	@AfterCompose(superclass=true)
	public void init(@BindingParam("matrizCurricular") MatrizCurricular matrizCurricular, @BindingParam("eletiva") boolean eletiva) {
		this.matrizCurricular = matrizCurricular;
		this.eletiva = eletiva;
		build();
	}
	
	@NotifyChange("col")
	private void build() {
		
		disciplinas = Lookup.dao(DisciplinaDao.class);
		
		if (matrizCurricular != null) {
			
			if (isEletiva()) {
				setCol(disciplinas.allEletivasByMatrizCurricular(matrizCurricular));
				
			} else {
				setCol(disciplinas.allByMatrizCurricular(matrizCurricular));
			}
		} else {
			
			Map<Pair<String, String>, Object> params = new HashMap<Pair<String, String>, Object>();
			
			if (getCodigo() != null) {
				params.put(new Pair<String, String>("codigo", "="), getCodigo());
			}
			
			if (getDescricao() != null && !getDescricao().isEmpty()) {
				params.put(new Pair<String, String>("descricao", "like"), getDescricao());
			}
			
			setCol(disciplinas.allBy(params));
		}
	}
	
	@NotifyChange("col")
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
	
	public void setEletiva(boolean eletiva) {
		this.eletiva = eletiva;
	}
	
	public boolean isEletiva() {
		return eletiva;
	}
}
