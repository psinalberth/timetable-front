package br.edu.ifma.csp.timetable.repository;

import java.util.List;

import br.edu.ifma.csp.timetable.model.Perfil;
import br.edu.ifma.csp.timetable.model.Transacao;

public interface Transacoes extends Repository<Transacao> {
	
	public List<Transacao> byPerfil(Perfil perfil);
}
