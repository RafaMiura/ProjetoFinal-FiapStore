package br.com.fiap.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	private static ConnectionFactory connection;

	private ConnectionFactory() {
	}

	// Singleton
	public static ConnectionFactory getInstance() {
		if (connection == null) {
			connection = new ConnectionFactory();
		}
		return connection;
	}

	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL", "", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

}
