package br.edu.ifma.csp.timetable.viewmodel.auth;

import org.mindrot.jbcrypt.BCrypt;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import br.edu.ifma.csp.timetable.dao.UsuarioDao;
import br.edu.ifma.csp.timetable.model.Usuario;
import br.edu.ifma.csp.timetable.repository.Usuarios;
import br.edu.ifma.csp.timetable.util.Lookup;

public class AuthViewModel {
	
	Usuarios usuarios;
	
	private Usuario usuario;
	
	private String login;
	private String senha;
	
	@Init
	private void init() {
		usuarios = Lookup.dao(UsuarioDao.class);
	}
	
	public boolean isLogged() {
		return Executions.getCurrent().getSession().getAttribute("usuario") != null;
	}
	
	public Usuario getUsuario() {
		
		setUsuario(null);
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = (Usuario) Executions.getCurrent().getSession().getAttribute("usuario");
	}
	
	@Command
	@NotifyChange({"logged", "usuario"})
	public void login() {
		
		Usuario usuario = usuarios.byLogin(getLogin());
		
		if (usuario != null) {
			
			senha = BCrypt.hashpw(senha, usuario.getSalt());
			
			if (senha.equals(usuario.getSenha())) {
				Executions.getCurrent().getSession().setAttribute("usuario", usuario);
				setUsuario(usuario);
			}
		}
	}
	
	@Command
	@NotifyChange({"logged", "usuario"})
	public void logout() {
		
		Executions.getCurrent().getSession().removeAttribute("usuario");
		Executions.getCurrent().getSession().invalidate();
	}
	
	@Command
	@NotifyChange({"login", "senha"})
	public void limpar() {
		
		setLogin(null);
		setSenha(null);
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
}
