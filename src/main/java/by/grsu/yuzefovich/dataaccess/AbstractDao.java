package by.grsu.yuzefovich.dataaccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public abstract class AbstractDao<E> implements IXmlDao<E> {
	
	private static final Logger log = Logger.getLogger(AbstractDao.class.getName());

	private String tableName;
	private Connection connection;
	
	public Connection getConnection() {
		return connection;
	}
	
	public AbstractDao(String tableName) {
		this.tableName = tableName;
		JDBCPostgreSQLConnection postgreSQL = new JDBCPostgreSQLConnection();
		this.connection = postgreSQL.getConnection();
	}
	
	public ResultSet getData() throws SQLException {
		Statement statement = connection.createStatement();
		String sqlRequest = "SELECT * FROM " + tableName;
		ResultSet resultSet = statement.executeQuery(sqlRequest);
		log.debug("Getting data from table " + tableName);
		return resultSet;
	}

}
