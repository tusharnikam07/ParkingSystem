package com.tushar.parkingsystem.Entities;

public interface Spot extends Entity {
	public int getId();

	public void setId(int id);

	public int getFloorId();

	public void setFloorId(int floorId);

	public String getStatus();

	public void setStatus(String status);

	public String getVehicleId();

	public void setVehicleId(String vehicleId);

	public Boolean getAvailability();

	public void setAvailability(Boolean availability);

}
