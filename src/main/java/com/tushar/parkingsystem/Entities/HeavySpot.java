package com.tushar.parkingsystem.Entities;

public class HeavySpot implements Spot {
	private int id;
	private int floorId;
	private String status;
	private String vehicleId;
	private Boolean availability;

	public HeavySpot() {
		super();
	}
	
	public HeavySpot(int id, int floorId, String status, String vehicleId) {
		super();
		this.id = id;
		this.floorId = floorId;
		this.status = status;
		this.vehicleId = vehicleId;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFloorId() {
		return floorId;
	}

	public void setFloorId(int floorId) {
		this.floorId = floorId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}


}
