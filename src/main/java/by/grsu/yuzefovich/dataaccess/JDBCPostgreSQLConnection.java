package by.grsu.yuzefovich.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class JDBCPostgreSQLConnection {
	
	private static final Logger log = Logger.getLogger(JDBCPostgreSQLConnection.class.getName());
	
	static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/DB";
	static final String USER = "postgres";
	static final String PASS = "root";
	
	private Connection connection;
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public JDBCPostgreSQLConnection() {
				 
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			log.error("PostgreSQL JDBC Driver is not found. Include it in your library path ");
			e.printStackTrace();
			return;
		}
	 
		log.debug("PostgreSQL JDBC Driver successfully connected");
		connection = null;
	 
		try {
			connection = DriverManager.getConnection(DB_URL, USER, PASS);	 
		} catch (SQLException e) {
			log.error("Connection to database failed");
			e.printStackTrace();
			return;
		}
	 
		if (connection != null) {
			log.debug("Successful connection to database");
		} else {
			log.error("Failed to make connection to database");
		}
		
	}	

}
