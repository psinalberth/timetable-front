package br.edu.ifma.csp.timetable.viewmodel.auth;

import org.mindrot.jbcrypt.BCrypt;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;

import br.edu.ifma.csp.timetable.dao.UsuarioDao;
import br.edu.ifma.csp.timetable.model.Usuario;
import br.edu.ifma.csp.timetable.repository.Usuarios;
import br.edu.ifma.csp.timetable.util.Lookup;

public class AuthViewModel {
	
	Usuarios usuarios = Lookup.dao(UsuarioDao.class);
	
	private Usuario usuario;
	
	private String login;
	private String senha;
	
	private String fragment;
	
	@Init
	@NotifyChange("fragment")
	private void init() {
		
		if (!isLogged()) {
			setFragment("../partials/zul/login.zul");
		} else {
			setFragment("../partials/zul/home.zul");
		}
	}
	
	public boolean isLogged() {
		return Executions.getCurrent().getSession().getAttribute("usuario") != null;
	}
	
	@NotifyChange("usuario")
	public Usuario getUsuario() {
		
		this.usuario = (Usuario) Executions.getCurrent().getSession().getAttribute("usuario");
		
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Command
	@NotifyChange({"usuario", "logged", "fragment"})
	public void login() {
		
		Usuario usuario = usuarios.byLogin(getLogin());
		
		if (usuario != null) {
			
			senha = BCrypt.hashpw(senha, usuario.getSalt());
			
			if (senha.equals(usuario.getSenha())) {
				
				Executions.getCurrent().getSession().setAttribute("usuario", usuario);
				
				this.usuario = usuario; 
				
				setLogin(null);
				setSenha(null);
				
				setFragment("../partials/zul/home.zul");
				
				Executions.sendRedirect("/");
				
			} else {
				
				setSenha(null);
				
				Clients.showNotification("Usuário inválido e/ou senha incorreta.", Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
				return;
			}
		} else if (login == null || login.isEmpty() || senha == null || senha.isEmpty()) {
				
			Clients.showNotification("Login e senha são obrigatórios.", Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
			return;
			
		} else {
			
			Clients.showNotification("Usuário não encontrado.", Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0);
			return;
		}
	}
	
	@Command
	@NotifyChange({"logged", "usuario", "fragment"})
	public void logout() {
		
		Executions.getCurrent().getSession().removeAttribute("usuario");
		Executions.getCurrent().getSession().removeAttribute("transacoes");
		Executions.getCurrent().setVoided(true);
		
		setFragment("../partials/zul/login.zul");
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

	public String getFragment() {
		return fragment;
	}

	public void setFragment(String fragment) {
		this.fragment = fragment;
	}
}
