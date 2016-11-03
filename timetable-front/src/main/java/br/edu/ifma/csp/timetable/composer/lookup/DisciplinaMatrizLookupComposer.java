package br.edu.ifma.csp.timetable.composer.lookup;

import org.zkoss.bind.annotation.Init;

import br.edu.ifma.csp.timetable.dao.DisciplinaDao;
import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.MatrizCurricular;
import br.edu.ifma.csp.timetable.repository.Disciplinas;
import br.edu.ifma.csp.timetable.util.Lookup;

public class DisciplinaMatrizLookupComposer extends LookupComposer<Disciplina> {

	private static final long serialVersionUID = 303181064364676134L;
	
	private Disciplinas disciplinas;
	
	private Integer codigo;
	private String sigla;
	private String descricao;
	private MatrizCurricular matrizCurricular;
	
	@Init
	public void init() {
		disciplinas = Lookup.dao(DisciplinaDao.class);
		setCol(disciplinas.all());
		getBinder().notifyChange(this, "*");
	}
	
	public void search() {
		
		getBinder().getView().getAttribute("value");
		//setMatrizCurricular();
		
		getBinder().notifyChange(this, "*");
		
		boolean like = false;
		
		if (getMatrizCurricular() != null) {
			
			setCol(disciplinas.allByMatrizCurricular(getMatrizCurricular()));
			
		} else
		
		if (getDescricao() != null && !getDescricao().isEmpty()) {
			
			like = true;
			
			setCol(disciplinas.allBy("descricao", getDescricao(), like));
			
		} else {
			setCol(disciplinas.all());
		}
		
		getBinder().notifyChange(this, "*");
	}
	
	public void clear() {
		
		setDescricao(null);
		
		search();
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
	
	public MatrizCurricular getMatrizCurricular() {
		return matrizCurricular;
	}
	
	public void setMatrizCurricular(MatrizCurricular matrizCurricular) {
		this.matrizCurricular = matrizCurricular;
	}
}