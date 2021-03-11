package com.tushar.parkingsystem.Entities;

public class Floor implements Entity {

	private int id;
	private int totalCapacity;
	private int twoWheeelerCapacity;
	private int smallCapacity;
	private int largeCapacity;
	private int heavyCapacity;
	private String status;

	public Floor(int id, int totalCapacity, int twoWheeelerCapacity, int smallCapacity, int largeCapacity,
			int heavyCapacity, String status) {
		super();
		this.id = id;
		this.totalCapacity = totalCapacity;
		this.twoWheeelerCapacity = twoWheeelerCapacity;
		this.smallCapacity = smallCapacity;
		this.largeCapacity = largeCapacity;
		this.heavyCapacity = heavyCapacity;
		this.status = status;
	}

	public Floor() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTotalCapacity() {
		return totalCapacity;
	}

	public void setTotalCapacity(int totalCapacity) {
		this.totalCapacity = totalCapacity;
	}

	public int getTwoWheeelerCapacity() {
		return twoWheeelerCapacity;
	}

	public void setTwoWheeelerCapacity(int twoWheeelerCapacity) {
		this.twoWheeelerCapacity = twoWheeelerCapacity;
	}

	public int getSmallCapacity() {
		return smallCapacity;
	}

	public void setSmallCapacity(int smallCapacity) {
		this.smallCapacity = smallCapacity;
	}

	public int getLargeCapacity() {
		return largeCapacity;
	}

	public void setLargeCapacity(int largeCapacity) {
		this.largeCapacity = largeCapacity;
	}

	public int getHeavyCapacity() {
		return heavyCapacity;
	}

	public void setHeavyCapacity(int heavyCapacity) {
		this.heavyCapacity = heavyCapacity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
