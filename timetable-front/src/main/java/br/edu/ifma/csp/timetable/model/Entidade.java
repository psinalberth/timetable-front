package br.edu.ifma.csp.timetable.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class Entidade implements Serializable {

	private static final long serialVersionUID = 109632090320375714L;

	@NotNull
	@Column(name="USUARIO_ULT_ALTERACAO")
	private String usuarioUltAlteracao;
	
	@NotNull
	@Column(name="DATA_ULT_ALTERACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUltAlteracao;
	
	@NotNull
	@Version
	@Column(name="VERSAO")
	private int versao;
	
	public abstract int getId();
	
	public Entidade() {
		
	}
	
	public String getUsuarioUltAlteracao() {
		return usuarioUltAlteracao;
	}
	
	public void setUsuarioUltAlteracao(String usuarioUltAlteracao) {
		this.usuarioUltAlteracao = usuarioUltAlteracao;
	}
	
	public Date getDataUltAlteracao() {
		return dataUltAlteracao;
	}
	
	public void setDataUltAlteracao(Date dataUltAlteracao) {
		this.dataUltAlteracao = dataUltAlteracao;
	}
	
	public int getVersao() {
		return versao;
	}
	
	public void setVersao(int versao) {
		this.versao = versao;
	}
}