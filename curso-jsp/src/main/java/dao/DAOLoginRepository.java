package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOLoginRepository {

	private Connection connection;
	
	public DAOLoginRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception {
		String sql = "select * from model_login where upper(login) = upper(?) and upper(senha) = upper(?) "; 
		/*O upper é utilizado no postgres apenas, serve para que o se ignore a diferença entre caracteres maisculos e minusculos*/
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			return true; /*autenticado */
		}
		
		return false; /*Não autenticado*/ 
	}
	
}
