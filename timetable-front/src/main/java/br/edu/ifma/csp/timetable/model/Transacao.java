package br.edu.ifma.csp.timetable.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="TRANSACAO")
public class Transacao extends Entidade {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@NotNull
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TRANSACAO")
	private int id;
	
	@NotBlank
	@Column(name="ICONE", length=40)
	private String icone;
	
	@NotBlank
	@Column(name="PATH", length=30)
	private String path;
	
	@NotBlank
	@Column(name="DESCRICAO", length=80)
	private String descricao;
	
	@ManyToMany(mappedBy = "transacoes")
	private List<Perfil> perfis = new ArrayList<Perfil>();
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getIcone() {
		return icone;
	}

	public void setIcone(String icone) {
		this.icone = icone;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
