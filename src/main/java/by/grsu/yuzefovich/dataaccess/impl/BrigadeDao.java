package by.grsu.yuzefovich.dataaccess.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.grsu.yuzefovich.dataaccess.AbstractDao;
import by.grsu.yuzefovich.datamodel.Brigade;

public class BrigadeDao extends AbstractDao<Brigade> {
	
	private static final Logger log = Logger.getLogger(BrigadeDao.class.getName());

	static final String TABLE_NAME = "brigade";

	public BrigadeDao() {
		super(TABLE_NAME);
	}

	@Override
	public void saveNew(Brigade newBrigade) {
		try {
			newBrigade.setId(System.nanoTime());
			String sqlRequest = "INSERT INTO brigade(id,number_of_workers) VALUES(?,?)";
			PreparedStatement preparedStatement = getConnection().prepareStatement(sqlRequest);
			preparedStatement.setLong(1, newBrigade.getId());
			preparedStatement.setInt(2, newBrigade.getNumberOfWorkers());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			log.debug("The brigade was saved successfully");
		}
		catch (SQLException e) {
			log.error("The brigade saving was failed");
			e.printStackTrace();
		}
	}

	@Override
	public void update(Brigade entity) {
		try {
			ResultSet rs = getData();
			while (rs.next()) {
				if (rs.getLong("id") == entity.getId()) {
					String sqlRequest = "UPDATE brigade SET number_of_workers=? WHERE id=?";
					PreparedStatement preparedStatement = getConnection().prepareStatement(sqlRequest);
					preparedStatement.setInt(1, entity.getNumberOfWorkers());
					preparedStatement.setLong(2, entity.getId());
					preparedStatement.executeUpdate();
					preparedStatement.close();
					log.debug("The brigade was updated successfully");
					break;
				}
			}
		}
		catch (SQLException e) {
			log.error("The brigade updating was failed");
			e.printStackTrace();
		}
	}

	@Override
	public Brigade get(Long id) {
		try {
			ResultSet rs = getData();
			while (rs.next()) {
				if (rs.getLong("id") == id) {
					Brigade brigade = new Brigade();
					brigade.setId(rs.getLong("id"));
					brigade.setNumberOfWorkers(rs.getInt("number_of_workers"));
					log.debug("The brigade was gotten successfully");
					return brigade;
				}
			}
		}
		catch (SQLException e) {
			log.error("Tge brigade getting was failed");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Brigade> getAll() {
		List<Brigade> brigades = new ArrayList<Brigade>();
		try {
			ResultSet rs = getData();
			while (rs.next()) {
				Brigade brigade = new Brigade();
				brigade.setId(rs.getLong("id"));
				brigade.setNumberOfWorkers(rs.getInt("number_of_workers"));
				brigades.add(brigade);
			}
			log.debug("The list of brigades was gotten successfully");
		}
		catch (SQLException e) {
			log.error("Failure in getting list of brigades or list is empty");
			e.printStackTrace();
		}
		return brigades;
	}

	@Override
	public void delete(Long id) {
		try {
			String sqlRequest = "DELETE FROM brigade WHERE id=?";
			PreparedStatement preparedStatement = getConnection().prepareStatement(sqlRequest);
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			log.debug("The brigade was deleted successfully");
		}
		catch (SQLException e) {
			log.error("The brigade deleting was failed");
			e.printStackTrace();
		}
	}


}
