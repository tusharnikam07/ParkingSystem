package com.tushar.parkingsystem.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParkingSystemUtility {

	public boolean isValidMobileNumber(String s) {
		Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
		Matcher m = p.matcher(s);
		return (m.matches());
	}

}
