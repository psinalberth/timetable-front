package br.edu.ifma.csp.timetable.dao;

import javax.ejb.Stateless;

import org.mindrot.jbcrypt.BCrypt;

import br.edu.ifma.csp.timetable.model.Usuario;
import br.edu.ifma.csp.timetable.repository.Usuarios;

@Stateless
public class UsuarioDao extends RepositoryDao<Usuario> implements Usuarios {
	
	@Override
	public void save(Usuario usuario) {
		
		String salt = BCrypt.gensalt();
		String senha = BCrypt.hashpw(usuario.getSenha(), salt);
		
		usuario.setSenha(senha);
		usuario.setSalt(salt);
		
		manager.persist(usuario);
	}
	
	public Usuario byLogin(String login) {
		return by("login", login);
	}
}
