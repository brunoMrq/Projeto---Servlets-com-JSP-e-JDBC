package servelets;

import java.io.IOException;

import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;



	@WebServlet( "/ServletUsuarioController") /* Mapeamento da URL que vem da tela */
	public class ServletUsuarioController extends HttpServlet {
		private static final long serialVersionUID = 1L;

		private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

		public ServletUsuarioController() {

		}

		// Recebe os dados pela url em parametros
		protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
			
			try {
			
			String acao = request.getParameter("acao");
			
			if ( acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				String idUser = request.getParameter("id");
				
				daoUsuarioRepository.deletarUser(idUser);
				
				request.setAttribute("msg", "Excluído com Sucesso!");
								
			}
			
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			
			}catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
				request.setAttribute("msg", e.getMessage());
				redirecionar.forward(request, response);
			}
			
		}

		// Recebe os dados por um formulário
		protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

			try {
			
			String msg = "Operação Realizada com Sucesso!";
				
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			
			ModelLogin modelLogin =new ModelLogin();
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			
			if(daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
			
			msg = "Já existe usuário como o mesmo login, informe outro login;";	
				
			}else {
				if (modelLogin.isNew()) {
					msg = "Gravado com Sucesso!";
				}else {
					msg = "Atualizado com Sucesso!";
				}
				
				modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin);

			}
						
			request.setAttribute("msg", msg);
			RequestDispatcher redirecionar = request.getRequestDispatcher("principal/usuario.jsp");
			request.setAttribute("modelLogin", modelLogin);
			redirecionar.forward(request, response);
			
			}catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
				request.setAttribute("msg", e.getMessage());
				redirecionar.forward(request, response);
			}
		}

	}
