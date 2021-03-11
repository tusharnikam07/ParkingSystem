package com.tushar.parkingsystem.Entities;

import java.util.ArrayList;
import java.util.List;

public class Element<T extends Entity> {

	private List<Object> valuesList = new ArrayList<Object>();

	public void addElement(Object object) {
		valuesList.add(object);
	}

	public Object getElement() {
		return valuesList.remove(0);
	}

	public int getlength() {
		return valuesList.size();
	}
}
