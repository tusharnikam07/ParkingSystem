package com.tushar.parkingsystem.Entities;

import java.sql.Timestamp;

public class Ticket implements Entity {
	private int id;
	private int gateId;
	private int vehicleId;
	private int floorId;
	private int spotid;
	private Timestamp issueTime;

	public Ticket() {
		super();
	}

	public Ticket(int id, int gateId, int vehicleId, int floorId, int spotid, Timestamp issueTime) {
		super();
		this.id = id;
		this.gateId = gateId;
		this.vehicleId = vehicleId;
		this.floorId = floorId;
		this.spotid = spotid;
		this.issueTime = issueTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGateId() {
		return gateId;
	}

	public void setGateId(int gateId) {
		this.gateId = gateId;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public int getFloorId() {
		return floorId;
	}

	public void setFloorId(int floorId) {
		this.floorId = floorId;
	}

	public int getSpotid() {
		return spotid;
	}

	public void setSpotid(int spotid) {
		this.spotid = spotid;
	}

	public Timestamp getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(Timestamp issueTime) {
		this.issueTime = issueTime;
	}

}
