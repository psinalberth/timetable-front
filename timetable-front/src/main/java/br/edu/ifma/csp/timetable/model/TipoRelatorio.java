package br.edu.ifma.csp.timetable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="TIPO_RELATORIO")
public class TipoRelatorio extends Entidade {
	
	private static final long serialVersionUID = -7180757923359490599L;
	
	@Id
	@Column(name="ID_TIPO_RELATORIO", columnDefinition="TINYINT(2)")
	private int id;
	
	@NotNull(message="A <b>descrição</b> é obrigatória.")
	@Column(name="DESCRICAO", length=100)
	private String descricao;
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}