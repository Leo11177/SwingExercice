package fr.leo.swing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JTextField;

public class SqlUtilitaires {	
	
	/**
	 * Creation d'une connexion
	 * @param loginUser
	 * @param passwordUser
	 * @return Connection  
	 **/
	private static  Connection getConnexion(String loginUser, String passwordUser) {		
		Properties props = new Properties();
		try ( FileInputStream fis = new FileInputStream( "src/conf.properties" ) ) {
			props.load( fis );
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}		
		try {
			Class.forName( props.getProperty( "jdbc.driver.class" ) );
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}		
		String url = props.getProperty( "jdbc.url" );
		String login = loginUser;
		String password = passwordUser;		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection( url, login, password );
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}		
		return connection;
	}	
	
	/** 
	 * Connection à la Bdd
	 * @param login
	 * @param password
	 * @return Connection
	 * Retourne une Connection si l'utilisateur et le mot de passe concordent
	 * Sinon retourne null
	 */
	public static Connection connectTo(JTextField login, JTextField password) {
		Connection connection = getConnexion(login.getText(), password.getText());
		return connection;	
	}
	
	/**
	 * @param connection
	 * @param sqlQuery
	 * @return ArrayList<String>ArrayList<String>
	 * Execute la requete Sql de type SELECT passée en parametre
	 * Retourne la liste des lignes retournées par la requete Sql passée en parametre
	 */
	public static ArrayList<String> executeQuery(Connection connection,String sqlQuery) {
		ArrayList<String> resultSetArrayList = new ArrayList<String>();
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlQuery)) {
			while (resultSet.next()) {
				String login = resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) ;
				resultSetArrayList.add(login);						
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return resultSetArrayList;
	}
	
	public static ArrayList<String> executeQueryEdition(Connection connection,String sqlQuery) {
		ArrayList<String> resultSetArrayList = new ArrayList<String>();
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlQuery)) {
			while (resultSet.next()) {
				String login = resultSet.getString(6) + " " + resultSet.getString(7) + " " + resultSet.getString(11) ;
				resultSetArrayList.add(login);						
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSetArrayList;
	}
	
	/**
	 * @param connection
	 * @param sqlQuery
	 * @return ArrayList<String>ArrayList<String>
	 * Execute la requete Sql de type INSERT passée en parametre
	 * Retourne un boolean à true si la requete a réussi
	 *  et un bolean à false si la requete a échoué
	 */
	public static Boolean executeInsert(Connection connection,String sqlQuery) {
		System.out.println(sqlQuery);
//		ArrayList<String> resultSetArrayList = new ArrayList<String>();
//		try (Statement statement = connection.createStatement();
//				ResultSet resultSet = statement.executeQuery(sqlQuery)) {
//			while (resultSet.next()) {
//				String login = resultSet.getString(3);
//				resultSetArrayList.add(login);						
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return true;
	}
	
	/**
	 * Execute la requete Sql de type DELETE passée en parametre.
	 * Retourne un boolean à true si la requete a réussi
	 *  et un bolean à false si la requete a échoué
	 * @param connection Connection 
	 * @param sqlQuery String 
	 * @return Boolean
	 * @throws SQLException 

	 */
	public static int executeDelete(Connection connection,String sqlQuery) throws SQLException {
		PreparedStatement st = connection.prepareStatement(sqlQuery);
         return (st.executeUpdate()); 
     }	
}
