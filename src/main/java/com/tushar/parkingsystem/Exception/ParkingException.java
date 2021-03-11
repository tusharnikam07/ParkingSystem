package com.tushar.parkingsystem.Exception;

public class ParkingException extends Exception {

	private static final long serialVersionUID = 1L;

	public ParkingException(String msg) {
		super(msg);
	}

	public ParkingException(String msg, Throwable e) {
		super(msg, e);
	}
}
