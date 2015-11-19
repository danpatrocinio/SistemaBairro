package br.com.app01.managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.app01.context.SessionContext;
import br.com.app01.model.dao.UsuarioDAOImpl;
import br.com.app01.model.entities.Usuario;
import br.com.app01.util.Message;
import br.com.app01.util.PasswordEncryptor;

@Named(value = "autenticacaoMB")
@SessionScoped
public class AutenticacaoMB implements Serializable {

	private static final long serialVersionUID = -2362252462400071528L;

	@Inject
	UsuarioDAOImpl usuarioDao;
	private String login;

	private String senha;

	public static Object getSessionObject(String objName) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext extCtx = ctx.getExternalContext();
		Map<String, Object> sessionMap = extCtx.getSessionMap();
		return sessionMap.get(objName);
	}

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}

	public Usuario getUsuario() {
		return SessionContext.getInstance().getUsuarioLogado();
	}

	public String logar() throws IOException {
		try {

			System.out.println(">>> senha: " + PasswordEncryptor.shaEncrypt("admin"));

			Usuario usuarioLogado = usuarioDao.login(getLogin(),
			        PasswordEncryptor.shaEncrypt(getSenha()));

			if (usuarioLogado != null) {
				limpar();

				SessionContext.getInstance().setAttribute("usuarioLogado", usuarioLogado);

				//Cria as sessões com informações do usuário
				FacesContext context = FacesContext.getCurrentInstance();
				HttpServletRequest request = (HttpServletRequest) context.getExternalContext()
				        .getRequest();
				HttpServletResponse response = (HttpServletResponse) context.getExternalContext()
				        .getResponse();

				response.sendRedirect(request.getContextPath() + "/index.faces");
			} else {
				FacesContext.getCurrentInstance().validationFailed();

				Message.errorMessage("Usuário e/ou senha inválido(s)");
			}

			limpar();
		} catch (Exception e) {
			Message.errorMessage(e.getMessage());
		}

		return "";
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	private void limpar() {
		setLogin(null);
		setSenha(null);
	}
}
