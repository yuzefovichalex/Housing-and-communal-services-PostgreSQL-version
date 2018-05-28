package by.grsu.yuzefovich.dataaccess.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.grsu.yuzefovich.dataaccess.AbstractDao;
import by.grsu.yuzefovich.datamodel.Tenant;

public class TenantDao extends AbstractDao<Tenant> {
	
	private static final Logger log = Logger.getLogger(TenantDao.class.getName());

	static final String TABLE_NAME = "tenants";

	public TenantDao() {
		super(TABLE_NAME);
	}

	@Override
	public void saveNew(Tenant newTenant) {
		try {
			newTenant.setId(System.nanoTime());
			String sqlRequest = "INSERT INTO tenants(id,name) VALUES(?,?)";
			PreparedStatement preparedStatement = getConnection().prepareStatement(sqlRequest);
			preparedStatement.setLong(1, newTenant.getId());
			preparedStatement.setString(2, newTenant.getName());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			log.debug("The tenant was saved successfully");
		}
		catch (SQLException e) {
			log.error("The tenant saving was failed");
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Tenant entity) {
		try {
			ResultSet rs = getData();
			while (rs.next()) {
				if (rs.getLong("id") == entity.getId()) {
					String sqlRequest = "UPDATE tenants SET name=? WHERE id=?";
					PreparedStatement preparedStatement = getConnection().prepareStatement(sqlRequest);
					preparedStatement.setString(1, entity.getName());
					preparedStatement.setLong(2, entity.getId());
					preparedStatement.executeUpdate();
					preparedStatement.close();
					log.debug("The tenant was updated successfully");
					break;
				}
			}
		}
		catch (SQLException e) {
			log.error("The tenant updating was failed");
			e.printStackTrace();
		}
	}

	@Override
	public Tenant get(Long id) {
		try {
			ResultSet rs = getData();
			while (rs.next()) {
				if (rs.getLong("id") == id) {
					Tenant tenant = new Tenant();
					tenant.setId(rs.getLong("id"));
					tenant.setName(rs.getString("name"));
					log.debug("The tenant was gotten successfully");
					return tenant;
				}
			}
		}
		catch (SQLException e) {
			log.error("The tenant getting was failed");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Tenant> getAll() {
		List<Tenant> tenants = new ArrayList<Tenant>();
		try {
			ResultSet rs = getData();
			while (rs.next()) {
				Tenant tenant = new Tenant();
				tenant.setId(rs.getLong("id"));
				tenant.setName(rs.getString("name"));
				tenants.add(tenant);
			}
			log.debug("The list of tenants was gotten successfully");
		}
		catch (SQLException e) {
			log.error("Failure in getting list of tenants or list is empty");
			e.printStackTrace();
		}
		return tenants;
	}

	@Override
	public void delete(Long id) {
		try {
			String sqlRequest = "DELETE FROM tenants WHERE id=?";
			PreparedStatement preparedStatement = getConnection().prepareStatement(sqlRequest);
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			log.debug("The tenant was deleted successfully");
		}
		catch (SQLException e) {
			log.error("The tenant deleting was failed");
			e.printStackTrace();
		}
	}



}
