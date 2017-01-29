package br.edu.ifma.csp.timetable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.edu.ifma.csp.timetable.annotation.Unique;

@Entity
@Table(name="USUARIO")
public class Usuario extends Entidade {

	private static final long serialVersionUID = 8398555830937987808L;
	
	@Id
	@NotNull
	@Column(name="ID_USUARIO")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotBlank(message="O <b>nome</b> é obrigatório.")
	@Column(name="NOME", length=80)
	private String nome;
	
	@Unique(message="O <b>login</b> selecionado já está em uso.")
	@NotBlank(message="O <b>login</b> é obrigatório.")
	@Column(name="LOGIN", length=20)
	private String login;
	
	@NotBlank(message="A <b>senha</b> é obrigatória.")
	@Column(name="SENHA", length=64)
	private String senha;
	
	@Column(name="SALT", length=64)
	private String salt;
	
	@OneToOne(fetch=FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name="ID_PERFIL", referencedColumnName="ID_PERFIL", columnDefinition="default")
	private Perfil perfil;

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public Perfil getPerfil() {
		return perfil;
	}
	
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
}