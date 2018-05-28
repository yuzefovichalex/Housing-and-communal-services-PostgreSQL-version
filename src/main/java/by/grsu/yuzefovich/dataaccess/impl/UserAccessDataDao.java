package by.grsu.yuzefovich.dataaccess.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.grsu.yuzefovich.dataaccess.AbstractDao;
import by.grsu.yuzefovich.datamodel.UserAccessData;

public class UserAccessDataDao extends AbstractDao<UserAccessData> {
	
	private static final Logger log = Logger.getLogger(UserAccessDataDao.class.getName());

	static final String TABLE_NAME = "logpass";

	public UserAccessDataDao() {
		super(TABLE_NAME);
	}

	@Override
	public void saveNew(UserAccessData newUserAccessData) {
		try {
			newUserAccessData.setId(System.nanoTime());
			String sqlRequest = "INSERT INTO logpass(id,login,password,tenant_id) VALUES(?,?,?,?)";
			PreparedStatement preparedStatement = getConnection().prepareStatement(sqlRequest);
			preparedStatement.setLong(1, newUserAccessData.getId());
			preparedStatement.setString(2, newUserAccessData.getLogin());
			preparedStatement.setString(3, newUserAccessData.getPassword());
			preparedStatement.setLong(4, newUserAccessData.getTenantId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			log.debug("The userAccessData was saved successfully");
		}
		catch (SQLException e) {
			log.error("The userAccessData saving was failed");
			e.printStackTrace();
		}
	}

	@Override
	public void update(UserAccessData entity) {
		try {
			ResultSet rs = getData();
			while (rs.next()) {
				if (rs.getLong("id") == entity.getId()) {
					String sqlRequest = "UPDATE logpass SET login=?, password=?, tenant_id=? WHERE id=?";
					PreparedStatement preparedStatement = getConnection().prepareStatement(sqlRequest);
					preparedStatement.setString(1, entity.getLogin());
					preparedStatement.setString(2, entity.getPassword());
					preparedStatement.setLong(3, entity.getTenantId());
					preparedStatement.setLong(4, entity.getId());
					preparedStatement.executeUpdate();
					preparedStatement.close();
					log.debug("The userAccessData was updated successfully");
					break;
				}
			}
		}
		catch (SQLException e) {
			log.error("The userAccessData updating was failed");
			e.printStackTrace();
		}
	}

	@Override
	public UserAccessData get(Long id) {
		try {
			ResultSet rs = getData();
			while (rs.next()) {
				if (rs.getLong("id") == id) {
					UserAccessData userAccessData = new UserAccessData();
					userAccessData.setId(rs.getLong("id"));
					userAccessData.setLogin(rs.getString("login"));
					userAccessData.setPassword(rs.getString("password"));
					userAccessData.setTenantId(rs.getLong("tenant_id"));
					log.debug("The userAccessData was gotten successfully");
					return userAccessData;
				}
			}
		}
		catch (SQLException e) {
			log.error("The userAccessData getting was failed");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<UserAccessData> getAll() {
		List<UserAccessData> logpasses = new ArrayList<UserAccessData>();
		try {
			ResultSet rs = getData();
			while (rs.next()) {
				UserAccessData userAccessData = new UserAccessData();
				userAccessData.setId(rs.getLong("id"));
				userAccessData.setLogin(rs.getString("login"));
				userAccessData.setPassword(rs.getString("password"));
				userAccessData.setTenantId(rs.getLong("tenant_id"));
				logpasses.add(userAccessData);
			}
			log.debug("The list of userAccessData was gotten successfully");
		}
		catch (SQLException e) {
			log.error("Failure in getting list of userAccessData or list is empty");
			e.printStackTrace();
		}
		return logpasses;
	}

	@Override
	public void delete(Long id) {
		try {
			String sqlRequest = "DELETE FROM logpass WHERE id=?";
			PreparedStatement preparedStatement = getConnection().prepareStatement(sqlRequest);
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			log.debug("The userAccessData was deleted successfully");
		}
		catch (SQLException e) {
			log.error("The userAccessData deleting was failed");
			e.printStackTrace();
		}
	}


}
