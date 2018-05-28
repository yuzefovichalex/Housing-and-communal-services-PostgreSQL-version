package by.grsu.yuzefovich.dataaccess.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.grsu.yuzefovich.dataaccess.AbstractDao;
import by.grsu.yuzefovich.datamodel.Request;

public class RequestDao extends AbstractDao<Request> {
	
	private static final Logger log = Logger.getLogger(RequestDao.class.getName());

	static final String TABLE_NAME = "requests";

	public RequestDao() {
		super(TABLE_NAME);
	}

	@Override
	public void saveNew(Request newRequest) {
		try {
			newRequest.setId(System.nanoTime());
			String sqlRequest = "INSERT INTO requests(id,type_of_work,scope_of_work,lead_time,is_accepted,tenant_id) VALUES(?,?,?,?,?,?)";
			PreparedStatement preparedStatement = getConnection().prepareStatement(sqlRequest);
			preparedStatement.setLong(1, newRequest.getId());
			preparedStatement.setString(2, newRequest.getTypeOfWork());
			preparedStatement.setInt(3, newRequest.getScopeOfWork());
			preparedStatement.setInt(4, newRequest.getLeadTime());
			preparedStatement.setBoolean(5, newRequest.getIsAccepted());
			preparedStatement.setLong(6, newRequest.getTenantId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			log.debug("The request was saved successfully");
		}
		catch (SQLException e) {
			log.error("The request saving was failed");
			e.printStackTrace();
		}
	}

	@Override
	public void update(Request entity) {
		try {
		ResultSet rs = getData();
			while (rs.next()) {
				if (rs.getLong("id") == entity.getId()) {
					String sqlRequest = "UPDATE requests SET type_of_work=?, scope_of_work=?, lead_time=?, is_accepted=?, tenant_id=?, brigade_id=? WHERE id=?";
					PreparedStatement preparedStatement = getConnection().prepareStatement(sqlRequest);
					preparedStatement.setString(1, entity.getTypeOfWork());
					preparedStatement.setInt(2, entity.getScopeOfWork());
					preparedStatement.setInt(3, entity.getLeadTime());
					preparedStatement.setBoolean(4, entity.getIsAccepted());
					preparedStatement.setLong(5, entity.getTenantId());
					if (entity.getIsAccepted())
						preparedStatement.setLong(6, entity.getBrigadeId());
					preparedStatement.setLong(7, entity.getId());
					preparedStatement.executeUpdate();
					preparedStatement.close();
					log.debug("The request was updated successfully");
					break;
				}
			}
		}
		catch (SQLException e) {
			log.error("The request updating was failed");
			e.printStackTrace();
		}
	}
	

	@Override
	public Request get(Long id) {
		try {
			ResultSet rs = getData();
			while (rs.next()) {
				if (rs.getLong("id") == id) {
					Request request = new Request();
					request.setId(rs.getLong("id"));
					request.setTypeOfWork(rs.getString("type_of_work"));
					request.setScopeOfWork(rs.getInt("scope_of_work"));
					request.setLeadTime(rs.getInt("lead_time"));
					request.setIsAccepted(rs.getBoolean("is_accepted"));
					request.setTenantId(rs.getLong("tenant_id"));
					if (request.getIsAccepted())
						request.setBrigadeId(rs.getLong("brigade_id"));
					log.debug("The request was gotten successfully");
					return request;
				}
			}
		}
		catch (SQLException e) {
			log.error("The request getting was failed");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Request> getAll() {
		List<Request> requests = new ArrayList<Request>();
		try {
			ResultSet rs = getData();
			while (rs.next()) {
				Request request = new Request();
				request.setId(rs.getLong("id"));
				request.setTypeOfWork(rs.getString("type_of_work"));
				request.setScopeOfWork(rs.getInt("scope_of_work"));
				request.setLeadTime(rs.getInt("lead_time"));
				request.setIsAccepted(rs.getBoolean("is_accepted"));
				request.setTenantId(rs.getLong("tenant_id"));
				request.setBrigadeId(rs.getLong("brigade_id"));
				requests.add(request);
			}
			log.debug("The list of requests was gotten successfully");
		}
		catch (SQLException e) {
			log.error("Failure in getting list of requests or list is empty");
			e.printStackTrace();
		}
		return requests;
	}

	@Override
	public void delete(Long id) {
		try {
			String sqlRequest = "DELETE FROM requests WHERE id=?";
			PreparedStatement preparedStatement = getConnection().prepareStatement(sqlRequest);
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			log.debug("The request was deleted succesfully");
		}
		catch (SQLException e) {
			log.error("The request deleting was failed");
			e.printStackTrace();
		}
	}



}
