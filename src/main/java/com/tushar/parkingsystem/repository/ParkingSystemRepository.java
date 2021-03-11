package com.tushar.parkingsystem.repository;

import java.sql.ResultSet;

import com.tushar.parkingsystem.Entities.Element;
import com.tushar.parkingsystem.Entities.Floor;
import com.tushar.parkingsystem.Entities.Gate;
import com.tushar.parkingsystem.Entities.Person;
import com.tushar.parkingsystem.Entities.Spot;
import com.tushar.parkingsystem.Entities.SpotType;
import com.tushar.parkingsystem.Entities.Ticket;
import com.tushar.parkingsystem.Entities.Vehicle;
import com.tushar.parkingsystem.Exception.ParkingException;

public interface ParkingSystemRepository {

	int insertFloor(Element<Floor> element) throws ParkingException;

	int insertVehicle(Element<Vehicle> element) throws ParkingException;

	int insertPerson(Element<Person> element) throws ParkingException;

	int insertTicket(Element<Ticket> element) throws ParkingException;

	int insertGate(Element<Gate> element) throws ParkingException;

	int insertSpot(Element<Spot> element) throws ParkingException;

	boolean deleteGate(Element<Gate> element) throws ParkingException;

	boolean deleteFloor(Element<Floor> element) throws ParkingException;

	boolean deleteVehicle(Element<Vehicle> element) throws ParkingException;

	boolean deletePerson(Element<Person> element) throws ParkingException;

	boolean deleteSpot(Element<Spot> element) throws ParkingException;

	Spot availableSpot(SpotType spotType, Spot spot) throws ParkingException;

	Boolean occupySpot(int spotId, int floorId, int vehicleID) throws ParkingException;

	Boolean releaseSpot(int spotId, int floorId, int vehicleID) throws ParkingException;

	int checkPerson(Person person) throws ParkingException;

	int checkVehicle(Vehicle vehicle) throws ParkingException;

	void updateVehicle(int vehicleId, int personId) throws ParkingException;

	int getSpotInformation(int floorId, String spotType, Boolean availability) throws ParkingException;

	ResultSet getFloors() throws ParkingException;

	public Boolean updateStatusFloor(int id, String Status) throws ParkingException;

	public Boolean updateStatusGate(int id, String Status) throws ParkingException;

	public Boolean updateStatusSpot(int id, int floorId, String Status) throws ParkingException;

	public int totalSpotsAtFloor(int floorId, String spotType) throws ParkingException;

	public void updateSpotCountAtFloor(int floorId, String spotType, int offset) throws ParkingException;
}
