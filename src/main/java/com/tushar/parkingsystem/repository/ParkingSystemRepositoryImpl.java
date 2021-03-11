package com.tushar.parkingsystem.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.tushar.parkingsystem.Entities.Element;
import com.tushar.parkingsystem.Entities.Floor;
import com.tushar.parkingsystem.Entities.Gate;
import com.tushar.parkingsystem.Entities.Person;
import com.tushar.parkingsystem.Entities.Spot;
import com.tushar.parkingsystem.Entities.SpotType;
import com.tushar.parkingsystem.Entities.Ticket;
import com.tushar.parkingsystem.Entities.Vehicle;
import com.tushar.parkingsystem.Exception.ParkingException;
import com.tushar.parkingsystem.constants.Status;

public class ParkingSystemRepositoryImpl implements ParkingSystemRepository {

	private Connection connection;

	public ParkingSystemRepositoryImpl(Connection connection) throws SQLException {
		super();
		this.connection = connection;
	}

	private int insert(Element<?> element, String DBTableName) throws ParkingException {
		int generatedId = 0;
		String query = "insert into " + DBTableName + " values(";
		int length = element.getlength();
		for (int i = 0; i < length; i++) {
			if (i == length - 1)
				query += "?)";
			else
				query += "?,";
		}
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			for (int i = 1; i <= length; i++) {
				Object object = element.getElement();
				if (object.getClass() == String.class)
					preparedStatement.setString(i, (String) object);
				else if (object.getClass() == Integer.class)
					preparedStatement.setInt(i, (Integer) object);
				else if (object.getClass() == Long.class)
					preparedStatement.setLong(i, (Long) object);
				else if (object.getClass() == Double.class)
					preparedStatement.setDouble(i, (Double) object);
				else if (object.getClass() == Time.class)
					preparedStatement.setTime(i, (Time) object);
				else if (object.getClass() == Boolean.class)
					preparedStatement.setBoolean(i, (Boolean) object);
				else if (object.getClass() == Date.class)
					preparedStatement.setDate(i, (Date) object);
				else if (object.getClass() == Timestamp.class) {
					preparedStatement.setTimestamp(i, (Timestamp) object);
				}
			}
			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next())
					generatedId = rs.getInt(1);
			}
			return generatedId;
		} catch (SQLException e) {
			throw new ParkingException("SOME ERROR OCCURED WHILE INSERTION PLEASE CHECK", e);
		}
	}

	public int insertFloor(Element<Floor> element) throws ParkingException {
		String DBTableName = "floor";
		int id = insert(element, DBTableName);
		return id;
	}

	public int insertVehicle(Element<Vehicle> element) throws ParkingException {
		String DBTableName = "vehicle(vehicleNo,type,personId)";
		int id = insert(element, DBTableName);
		return id;
	}

	public int insertPerson(Element<Person> element) throws ParkingException {
		String DBTableName = "person(personId,idType,firstName,lastName,mobileNo,address)";
		int id = insert(element, DBTableName);
		return id;
	}

	public int insertTicket(Element<Ticket> element) throws ParkingException {
		String DBTableName = "ticket (gateId, vehicleId, floorId, spotId , IssueTime)";
		int id = insert(element, DBTableName);
		return id;

	}

	public int insertGate(Element<Gate> element) throws ParkingException {
		String DBTableName = "gate";
		int id = insert(element, DBTableName);
		return id;

	}

	public int insertSpot(Element<Spot> element) throws ParkingException {
		String DBTableName = "spot(id , floorId , type , availability,status)";
		int id = insert(element, DBTableName);
		return id;
	}

	public boolean delete(Element<?> element, String DBTableName) throws ParkingException {
		StringBuilder query = new StringBuilder();
		query.append("delete from ");
		query.append(DBTableName);
		query.append(" where ");
		List<Object> values = new ArrayList<Object>();

		int length = element.getlength();
		for (int i = 0; i < length; i++) {
			Object object = element.getElement();
			if (i % 2 == 0) {
				String parameterName = (String) object;
				query.append(parameterName + " =");
			} else {
				if (i == length - 1)
					query.append(" ? ");
				else
					query.append(" ? and ");
				values.add(object);
			}
		}
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
			for (int i = 0; i < values.size(); i++) {
				Object object = values.get(i);
				if (object.getClass() == Integer.class)
					preparedStatement.setInt(i + 1, (Integer) object);
				else if (object.getClass() == String.class)
					preparedStatement.setString(i + 1, (String) object);
				else if (object.getClass() == Long.class)
					preparedStatement.setLong(i + 1, (Long) object);
				else if (object.getClass() == Double.class)
					preparedStatement.setDouble(i + 1, (Double) object);
				else if (object.getClass() == Time.class)
					preparedStatement.setTime(i + 1, (Time) object);
				else if (object.getClass() == Boolean.class)
					preparedStatement.setBoolean(i + 1, (Boolean) object);
			}
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new ParkingException("SOME ERROR OCCURED WHILE REMOVING PLEASE CHECK", e);
		}

	}

	public boolean deleteGate(Element<Gate> element) throws ParkingException {

		String DBTableName = "gate";
		return delete(element, DBTableName);

	}

	public boolean deleteFloor(Element<Floor> element) throws ParkingException {
		String DBTableName = "Floor";
		return delete(element, DBTableName);

	}

	public boolean deleteVehicle(Element<Vehicle> element) throws ParkingException {
		String DBTableName = "Vehicle";
		return delete(element, DBTableName);

	}

	public boolean deletePerson(Element<Person> element) throws ParkingException {
		String DBTableName = "Person";
		return delete(element, DBTableName);

	}

	public boolean deleteSpot(Element<Spot> element) throws ParkingException {
		String DBTableName = "spot";
		boolean bool = delete(element, DBTableName);
		return bool;

	}

	public Spot availableSpot(SpotType spotType, Spot spot) throws ParkingException {
		String query = "select * from spot where type = ? and availability = 1 and status = ? limit 1";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, spotType.toString());
			preparedStatement.setString(2, Status.Active.toString());
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				spot.setId(rs.getInt(1));
				spot.setFloorId(rs.getInt(2));
			}
			return spot;
		} catch (Exception e) {
			throw new ParkingException("SOME ERROR OCCURED WHILE SELECTING DATA PLEASE CHECK", e);
		}
	}

	private Boolean spotOperation(int spotId, int floorId, int vehicleId, boolean spotStatus) throws ParkingException {
		String query = "update spot set availability = ? , vehicleId = ? where floorId = ? and id = ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setBoolean(1, spotStatus);
			if (vehicleId == 0)
				preparedStatement.setNull(2, Types.INTEGER);
			else
				preparedStatement.setInt(2, vehicleId);
			preparedStatement.setInt(3, floorId);
			preparedStatement.setInt(4, spotId);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new ParkingException("SOME ERROR OCCURED WHILE UPDATING DATA PLEASE CHECK", e);
		}
	}

	@Override
	public Boolean occupySpot(int spotId, int floorId, int vehicleId) throws ParkingException {
		return spotOperation(spotId, floorId, vehicleId, false);
	}

	@Override
	public Boolean releaseSpot(int spotId, int floorId, int vehicleId) throws ParkingException {
		return spotOperation(spotId, floorId, vehicleId, true);
	}

	private ResultSet select(String DBTableName, String columnName, Object columnValue) throws ParkingException {
		String query = "select * from " + DBTableName + " where " + columnName + " = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			if (columnValue.getClass() == Integer.class) {
				preparedStatement.setInt(1, (int) columnValue);
			} else if (columnValue.getClass() == String.class) {
				preparedStatement.setString(1, (String) columnValue);
			} else if (columnValue.getClass() == Long.class)
				preparedStatement.setLong(1, (Long) columnValue);
			else if (columnValue.getClass() == Double.class)
				preparedStatement.setDouble(1, (Double) columnValue);
			else if (columnValue.getClass() == Time.class)
				preparedStatement.setTime(1, (Time) columnValue);
			else if (columnValue.getClass() == Boolean.class)
				preparedStatement.setBoolean(1, (Boolean) columnValue);

			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;
		} catch (SQLException e) {
			throw new ParkingException("SOME ERROR OCCURED WHILE FETCHING DATA PLEASE CHECK", e);
		}

	}

	@Override
	public int checkPerson(Person person) throws ParkingException {
		String DBTableName = "person";
		ResultSet resultSet = select(DBTableName, "personId", person.getPersonId());

		try {
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			throw new ParkingException("SOME ERROR OCCURED WHILE INSERTION PLEASE CHECK", e);
		}
	}

	@Override
	public int checkVehicle(Vehicle vehicle) throws ParkingException {
		String DBTableName = "vehicle";
		try {
			ResultSet resultSet = select(DBTableName, "vehicleNo", vehicle.getVehicleNo());
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			throw new ParkingException("SOME ERROR OCCURED WHILE INSERTION PLEASE CHECK", e);
		}

	}

	@Override
	public void updateVehicle(int vehicleId, int personId) throws ParkingException {
		String query = "update vehicle set personId = ? where id = ?";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, personId);
			preparedStatement.setInt(2, vehicleId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new ParkingException("SOME ERROR OCCURED WHILE INSERTION PLEASE CHECK", e);
		}
	}

	@Override
	public int getSpotInformation(int floorId, String spotType, Boolean availability) throws ParkingException {
		int count = 0;
		String query = "select count(*) from spot where floorId = ? and type = ? and availability = ? and status= ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, floorId);
			preparedStatement.setString(2, spotType);
			preparedStatement.setBoolean(3, availability);
			preparedStatement.setString(4, Status.Active.toString());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			throw new ParkingException("SOME ERROR OCCURED WHILE INSERTION PLEASE CHECK", e);
		}
	}

	@Override
	public ResultSet getFloors() throws ParkingException {
		String query = "select id from floor where status = ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, Status.Active.toString());
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;
		} catch (SQLException e) {
			throw new ParkingException("SOME ERROR OCCURED WHILE INSERTION PLEASE CHECK", e);
		}
	}

	private Boolean updateStatus(String DBtableName, String status, int id, String columnName, Object object)
			throws ParkingException {
		String query = "update " + DBtableName + " set status= ? where id = ?";
		if (!columnName.equals("")) {
			query += " and " + columnName + " = ?";
		}

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, status);
			preparedStatement.setInt(2, id);
			if (!columnName.equals("")) {
				if (object.getClass() == String.class)
					preparedStatement.setString(3, (String) object);
				else if (object.getClass() == Integer.class)
					preparedStatement.setInt(3, (Integer) object);
				else if (object.getClass() == Long.class)
					preparedStatement.setLong(3, (Long) object);
				else if (object.getClass() == Double.class)
					preparedStatement.setDouble(3, (Double) object);
				else if (object.getClass() == Time.class)
					preparedStatement.setTime(3, (Time) object);
				else if (object.getClass() == Boolean.class)
					preparedStatement.setBoolean(3, (Boolean) object);
			}

			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new ParkingException("SOME ERROR OCCURED WHILE INSERTION PLEASE CHECK", e);

		}
	}

	public Boolean updateStatusFloor(int id, String status) throws ParkingException {
		String DBTableName = "floor";
		boolean bool = updateStatus(DBTableName, status, id, "", null);

		String query = "update spot set status = ? where floorId = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, status);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return bool;

	}

	public Boolean updateStatusGate(int id, String Status) throws ParkingException {
		String DBTableName = "gate";
		return updateStatus(DBTableName, Status, id, "", null);
	}

	public Boolean updateStatusSpot(int id, int floorId, String Status) throws ParkingException {
		String DBTableName = "spot";
		return updateStatus(DBTableName, Status, id, "floorId", floorId);
	}

	public int totalSpotsAtFloor(int floorId, String spotType) throws ParkingException {
		String query = "select count(*) from spot where floorId = ? and type = ?";
		int count = 0;
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, floorId);
			preparedStatement.setString(2, spotType);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			throw new ParkingException("SOME ERROR OCCURED WHILE INSERTION PLEASE CHECK", e);
		}

	}

	public void updateSpotCountAtFloor(int floorId, String spotType, int offset) throws ParkingException {
		String query = "update floor set totalCapacity=totalCapacity+ ? , " + spotType + " = " + spotType
				+ " + ? where id= ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, offset);
			preparedStatement.setInt(3, floorId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new ParkingException("SOME ERROR OCCURED WHILE UPDATION ", e);
		}

	}

}
