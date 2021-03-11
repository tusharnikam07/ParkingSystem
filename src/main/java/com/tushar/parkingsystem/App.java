package com.tushar.parkingsystem;

import static com.tushar.parkingsystem.constants.ParkingConstants.PassWord;
import static com.tushar.parkingsystem.constants.ParkingConstants.URL;
import static com.tushar.parkingsystem.constants.ParkingConstants.UserName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tushar.parkingsystem.service.ParkingSystemService;

public class App {

	private static final Logger logger = LogManager.getLogger(App.class);

	private static Connection getConnection() {
		logger.info("User Has Entered To Create a Connection");
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection connection = DriverManager.getConnection(URL, UserName, PassWord);
			return connection;
		} catch (Exception e) {
			logger.error("Connection Could not be Established Due to Some Error : {}", e);
		}
		logger.info("Connection Created Successfully");
		return null;
	}

	public static void main(String[] args) throws SQLException {
		System.out.println(
				"************************************************************************************************************************************************************************\n\n \t\t\t\t\t\t\t\tPARKING SYSTEM\n\n************************************************************************************************************************************************************************");
		System.out.println(
				"The Choices In Our System Are :\n\n 1. Check Spot Availability  2. Occupy Spot  3. Release Spot  4. View Parking System Status \n\nOther Functions Are :\n \n 5. Add Gate  6. Add Floor  7. Add Person  8. Add Vehicle  9. Add Spot \n 10. Remove Gate  11. Remove Vehicle  12. Remove Person  13. Remove Floor  14. Remove Spot  \n 15. Change Floor Status  16. Change Gate Status  17. Change Spot Status\n\n ");
		Connection connection = getConnection();
		ParkingSystemService service = new ParkingSystemService(connection);
		Scanner scanner = new Scanner(System.in);
		int choice;
		service.printSystemInformation();
		do {
			System.out.println("\n\nEnter Choice : ");
			choice = scanner.nextInt();
			switch (choice) {
			case 1:
				service.checkSpotAvailability();
				break;
			case 2:
				service.createTicket();
				break;
			case 3:
				service.releaseSpot();
				break;
			case 4:
				service.printSystemInformation();
				break;
			case 5:
				service.addGate();
				break;
			case 6:
				service.addfloor();
				break;
			case 7:
				service.addPerson(true);
				break;
			case 8:
				service.addVehicle(0, true);
				break;
			case 9:
				service.addSpot();
				break;
			case 10:
				service.removeGate();
				break;
			case 11:
				service.removeVehicle();
				break;
			case 12:
				service.removePerson();
				break;
			case 13:
				service.removeFloor();
				break;
			case 14:
				service.removeSpot();
				break;
			case 15:
				service.changeFloorStatus();
				break;
			case 16:
				service.changeGateStatus();
				break;
			case 17:
				service.changeSpotStatus();
				break;
			default:
				System.out.println("Wrong Choice Entered");
			}
		} while (true);
	}
}
