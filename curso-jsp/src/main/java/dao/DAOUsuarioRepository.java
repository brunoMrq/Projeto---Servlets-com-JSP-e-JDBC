package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection connection;
	
	public DAOUsuarioRepository() {
	
		connection = SingleConnectionBanco.getConnection();
		
	}
	
	public ModelLogin gravarUsuario(ModelLogin objeto) throws Exception{
		
		if (objeto.isNew()) {
		
		String sql = "INSERT INTO model_login(login, senha, nome, email) VALUES (?, ?, ?, ?);";
		
		PreparedStatement prepareSql = connection.prepareStatement(sql);
		
		prepareSql.setString(1, objeto.getLogin());
		prepareSql.setString(2, objeto.getSenha());
		prepareSql.setString(3, objeto.getNome());
		prepareSql.setString(4, objeto.getEmail());
		
		prepareSql.execute();
		
		connection.commit();
		
		}else {
		
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=? WHERE id = "+objeto.getId()+";";
			
			PreparedStatement prepareSql = connection.prepareStatement(sql);
			
			prepareSql.setString(1, objeto.getLogin());
			prepareSql.setString(2, objeto.getSenha());
			prepareSql.setString(3, objeto.getNome());
			prepareSql.setString(4, objeto.getEmail());
			
			prepareSql.executeUpdate();
			
			connection.commit();
			
		}
		
		return this.consultaUsuario(objeto.getLogin());
	}
	
	public ModelLogin consultaUsuario(String login) throws Exception {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper('"+login+"')";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next())/*Se tem resultado*/ {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			
		}
		
		return modelLogin;
	}
	
	public boolean validarLogin(String login) throws Exception {
		String sql = "select count(1) > 0 as existe from model_login where upper(login) = upper('"+login+"');";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		resultado.next();
		return resultado.getBoolean("existe");
		
	}
	
	public void deletarUser(String idUser) throws Exception {
		String sql = "DELETE FROM model_login WHERE id = ?;";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setLong(1, Long.parseLong(idUser));
		statement.executeUpdate();
		
		connection.commit();
		
		
		
	}
}
