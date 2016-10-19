package br.edu.ifma.csp.timetable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="TIPO_LOCAL")
public class TipoLocal extends Entidade {

	private static final long serialVersionUID = -5827325188568409472L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TIPO_LOCAL")
	private int id;
	
	@Column(name="DESCRICAO", length=20)
	@NotBlank(message="A descrição é obrigatória.")
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