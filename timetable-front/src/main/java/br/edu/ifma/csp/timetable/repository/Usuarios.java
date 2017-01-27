package br.edu.ifma.csp.timetable.repository;

import br.edu.ifma.csp.timetable.model.Usuario;

public interface Usuarios extends Repository<Usuario> {
	
	public Usuario byLogin(String login);
}
