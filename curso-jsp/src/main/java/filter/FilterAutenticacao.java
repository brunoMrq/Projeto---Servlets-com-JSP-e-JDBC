package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connection.SingleConnectionBanco;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/principal/*" }) /* Intercepta todas as requisições que vierem do projeto ou mapeamento */
public class FilterAutenticacao implements Filter {

	private static Connection connection;

	public FilterAutenticacao() {
	}

	/* Encerra os processos quando o servidor é parado */
	/* Mataria os processos de conexão com o banco de dados */
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* Intercepta as requisições e as resposta no sistema */
	/* Tudo que fizer no sistema passa por ele */
	/*
	 * Validação de autenticação Dar comit e rolback de transações do banco Validae
	 * e fazer direcionamento de páginas
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();

			String usuarioLogado = (String) session.getAttribute("usuario");

			String urlParaAutenticar = req.getServletPath();

			if (usuarioLogado == null || (usuarioLogado != null && usuarioLogado.isEmpty())
					&& !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")
					&& !urlParaAutenticar.equalsIgnoreCase("/ServletLogin")) {

				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor realize o login");
				redireciona.forward(request, response);
				return;
			} else {
				chain.doFilter(request, response);

			}
			connection.commit(); /*Deu tudo certo, então comita as alterações no banco de dados*/
		} catch (Exception e) {
			e.printStackTrace();
			
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	/* Inicia os processos ou recursos quando o servidor sobe o projeto */
	/* Inicia a conexão com banco */
	public void init(FilterConfig fconfig) throws ServletException {
		connection = SingleConnectionBanco.getConnection();
	}
}
