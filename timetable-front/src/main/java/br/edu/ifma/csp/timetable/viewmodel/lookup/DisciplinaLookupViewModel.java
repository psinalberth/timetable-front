package br.edu.ifma.csp.timetable.viewmodel.lookup;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.Pair;

import br.edu.ifma.csp.timetable.dao.DisciplinaDao;
import br.edu.ifma.csp.timetable.model.DetalheTimetable;
import br.edu.ifma.csp.timetable.model.Disciplina;
import br.edu.ifma.csp.timetable.model.TipoCriterioTimetable;
import br.edu.ifma.csp.timetable.repository.Disciplinas;
import br.edu.ifma.csp.timetable.util.Lookup;

public class DisciplinaLookupViewModel extends LookupViewModel<Disciplina> {
	
	private Disciplinas disciplinas = Lookup.dao(DisciplinaDao.class);
	
	private DetalheTimetable detalhe;
	private Integer codigo;
	private String sigla;
	private String descricao;
	
	@AfterCompose(superclass=true)
	public void init(@BindingParam("detalhe") DetalheTimetable detalhe) {
		this.detalhe = detalhe;
		build();
	}
	
	@NotifyChange("col")
	private void build() {
		
		if (getDetalhe() != null && getDetalhe().getPeriodo() != null) {
			
			if (getDetalhe().getTipoCriterioTimetable() != null && getDetalhe().getTipoCriterioTimetable().getId() == TipoCriterioTimetable.DISCIPLINA_ELETIVA) {
				setCol(disciplinas.allEletivasByMatrizCurricular(getDetalhe().getPeriodo().getMatrizCurricular(), getDetalhe().getPeriodo()));
				
			} else {
				setCol(disciplinas.allByMatrizCurricular(getDetalhe().getPeriodo().getMatrizCurricular()));
			}
			
			if (codigo != null) {
				setCol(getCol().stream().filter(d -> d.getCodigo() == codigo).collect(Collectors.toList()));
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

	public DetalheTimetable getDetalhe() {
		return detalhe;
	}

	public void setDetalhe(DetalheTimetable detalhe) {
		this.detalhe = detalhe;
	}
}