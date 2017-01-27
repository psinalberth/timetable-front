package br.edu.ifma.csp.timetable.viewmodel.auth;

import org.mindrot.jbcrypt.BCrypt;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import br.edu.ifma.csp.timetable.dao.UsuarioDao;
import br.edu.ifma.csp.timetable.model.Usuario;
import br.edu.ifma.csp.timetable.repository.Usuarios;
import br.edu.ifma.csp.timetable.util.Lookup;

public class AuthViewModel {
	
	Usuarios usuarios;
	
	private String login;
	private String senha;
	
	@Init
	private void init() {
		usuarios = Lookup.dao(UsuarioDao.class);
	}
	
	public boolean isLogged() {
		return Executions.getCurrent().getSession().getAttribute("login") != null;
	}
	
	@Command
	@NotifyChange("logged")
	public void login() {
		
		Usuario usuario = usuarios.byLogin(getLogin());
		
		if (usuario != null) {
			
			senha = BCrypt.hashpw(senha, usuario.getSalt());
			
			if (senha.equals(usuario.getSenha())) {
				Executions.getCurrent().getSession().setAttribute("login", usuario.getLogin());
			}
		}
	}
	
	@GlobalCommand
	public void logout() {
		
		Executions.getCurrent().getSession().removeAttribute("login");
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
