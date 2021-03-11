package com.tushar.parkingsystem.service;

import static com.tushar.parkingsystem.constants.ParkingConstants.AadharCard;
import static com.tushar.parkingsystem.constants.ParkingConstants.DrivingLicense;
import static com.tushar.parkingsystem.constants.ParkingConstants.HeavySpotCost;
import static com.tushar.parkingsystem.constants.ParkingConstants.HeavySpotFine;
import static com.tushar.parkingsystem.constants.ParkingConstants.LargeSpotCost;
import static com.tushar.parkingsystem.constants.ParkingConstants.LargeSpotFine;
import static com.tushar.parkingsystem.constants.ParkingConstants.PanCard;
import static com.tushar.parkingsystem.constants.ParkingConstants.SmallSpotCost;
import static com.tushar.parkingsystem.constants.ParkingConstants.SmallSpotFine;
import static com.tushar.parkingsystem.constants.ParkingConstants.TwoWheelerSpotCost;
import static com.tushar.parkingsystem.constants.ParkingConstants.TwoWheelerSpotFine;
import static com.tushar.parkingsystem.constants.ParkingConstants.VotingCard;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.EnumSet;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tushar.parkingsystem.Entities.Element;
import com.tushar.parkingsystem.Entities.Floor;
import com.tushar.parkingsystem.Entities.Gate;
import com.tushar.parkingsystem.Entities.HeavySpot;
import com.tushar.parkingsystem.Entities.LargeSpot;
import com.tushar.parkingsystem.Entities.Person;
import com.tushar.parkingsystem.Entities.SmallSpot;
import com.tushar.parkingsystem.Entities.Spot;
import com.tushar.parkingsystem.Entities.SpotType;
import com.tushar.parkingsystem.Entities.Ticket;
import com.tushar.parkingsystem.Entities.TwoWheelerSpot;
import com.tushar.parkingsystem.Entities.Vehicle;
import com.tushar.parkingsystem.Exception.ParkingException;
import com.tushar.parkingsystem.constants.GateType;
import com.tushar.parkingsystem.constants.Status;
import com.tushar.parkingsystem.repository.ParkingSystemRepository;
import com.tushar.parkingsystem.repository.ParkingSystemRepositoryImpl;
import com.tushar.parkingsystem.utility.ParkingSystemUtility;

public class ParkingSystemService {
	ParkingSystemRepository repository;
	private static final Logger logger = LogManager.getLogger(ParkingSystemService.class);
	ParkingSystemUtility utility = new ParkingSystemUtility();
	Scanner scanner = new Scanner(System.in);

	public ParkingSystemService(Connection connection) throws SQLException {
		super();
		repository = new ParkingSystemRepositoryImpl(connection);
	}

	public void addGate() throws SQLException {
		scanner.nextLine();
		System.out.println(
				"Please Confirm if you want to proceed by pressing y or yes else press any other key to abort");
		String selection = scanner.nextLine();
		if (selection.toLowerCase().equals("y") || selection.toLowerCase().equals("yes")) {
			logger.info("User is creating a new Gate : ");
			System.out.println("enter GateID");
			Gate gate = new Gate();
			gate.setId(scanner.nextInt());
			scanner.nextLine();
			System.out.println("Please select the Gate Type you want to Add\n for  1 : IN   2. OUT  3 : IN AND OUT ");
			Boolean status = true;
			while (status) {
				int choice = scanner.nextInt();
				switch (choice) {
				case 1:
					gate.setType(GateType.In.toString());
					status = false;
					break;
				case 2:
					gate.setType(GateType.Out.toString());
					status = false;
					break;
				case 3:
					gate.setType(GateType.InOut.toString());
					status = false;
					break;
				default:
					System.out.println("Wrong Choice Entered Please Enter Choice Again");
				}
			}
			System.out.println(
					"Please Select One of the Following Choices to Set Gate Status \n 1. Active    2. InActive");
			status = true;
			while (status) {
				int choice = scanner.nextInt();
				switch (choice) {
				case 1:
					gate.setStatus(Status.Active.toString());
					status = false;
					break;
				case 2:
					gate.setStatus(Status.InActive.toString());
					status = false;
					break;
				default:
					System.out.println("Wrong Choice Entered Please Enter Choice Again");
				}
			}

			Element<Gate> element = new Element<Gate>();
			element.addElement(gate.getId());
			element.addElement(gate.getType());
			element.addElement(gate.getStatus());

			int gateId;
			try {
				gateId = repository.insertGate(element);
				if (gateId >= 0) {
					System.out.printf("\nGate %d of Type %s added SuccessFully", gate.getId(), gate.getType());
				}
			} catch (ParkingException e) {
				logger.error("Exception occured while adding gate to database {}", e);
				System.err.println(e.getMessage());
			}
			logger.info("Gate {} Creation Succesfull", gate.getId());
		}
	}

	public int addPerson(boolean isCalledbyUser) {
		if (isCalledbyUser) {
			scanner.nextLine();
			System.out.println(
					"Please Confirm if you want to proceed by pressing y or yes else press any other key to abort");
			String selection = scanner.nextLine();
			if (!selection.toLowerCase().equals("y") && !selection.toLowerCase().equals("yes")) {
				return 0;
			}
		}
		int id = 0;
		logger.info("User is creating a new Person : ");
		Person person = new Person();
		scanner.nextLine();
		System.out.println("\nenter Person ID No ");

		person.setPersonId(scanner.nextLine());
		try {
			id = repository.checkPerson(person);
		} catch (ParkingException e1) {
			logger.error("Error While Checking If the person is already present in Database {} ", e1);
			System.err.println(e1.getMessage());
		}
		if (id == 0) {
			System.out.println(
					"Select person ID Type :\n 1. Aadhar Card   2. Pan Card   3. Driving License   4. Voting ID");
			String idType = "";
			boolean status = true;
			while (status) {
				switch (scanner.nextInt()) {
				case 1:
					idType = AadharCard;
					status = false;
					break;
				case 2:
					idType = PanCard;
					status = false;
					break;
				case 3:
					idType = DrivingLicense;
					status = false;
					break;
				case 4:
					idType = VotingCard;
					status = false;
					break;
				default:
					System.out.println("Wrong Choice Entered Please Enter Choice Again");
				}
			}

			person.setIdType(idType);
			scanner.nextLine();
			System.out.println("enter Person FirstName");
			person.setFirstName(scanner.nextLine());
			System.out.println("enter Person LastName");
			person.setLastName(scanner.nextLine());
			System.out.println("enter Person Address");
			person.setAddress(scanner.nextLine());

			Long mobileNo;
			while (true) {
				System.out.println("enter Person MobileNo");
				mobileNo = scanner.nextLong();
				if (utility.isValidMobileNumber(mobileNo.toString()))
					break;
				else {
					System.out.println("Entered Mobile Number is Wrong Please Enter Again");
				}
			}
			person.setMobileNo(mobileNo);
			Element<Person> element = new Element<Person>();
			element.addElement(person.getPersonId());
			element.addElement(person.getIdType());
			element.addElement(person.getFirstName());
			element.addElement(person.getLastName());
			element.addElement(person.getMobileNo());
			element.addElement(person.getAddress());
			try {
				id = repository.insertPerson(element);
				logger.info(" Person {} Creation SuccesFull: ", person.getPersonId());
				System.out.printf("\nNew Person with ID %s Added Succesfully\n", person.getPersonId());
				return id;
			} catch (ParkingException e) {
				logger.error("Error While Inserting Person Into The Database {}", e);
				System.err.println(e.getMessage());
			}
		} else {
			logger.info(" Person {} is an Existing Customer Data Fetched From Database: ", person.getPersonId());
			System.out.println("Person is a old customer data fetched from database\n");
		}

		return id;
	}

	private void printTicketDetails(Ticket ticket, String vehicleNumber, Spot spot) {
		logger.info("User is Printing details About ticket {} ", ticket.getId());
		System.out.println(
				"***********************************************************************************************************************\n\nTicket Details Are : \n");
		int ticketCost = 0, fine = 0;
		long millis = ticket.getIssueTime().getTime();
		if (spot.getClass() == HeavySpot.class) {
			ticketCost = HeavySpotCost;
			fine = HeavySpotFine;
		} else if (spot.getClass() == LargeSpot.class) {
			ticketCost = LargeSpotCost;
			fine = LargeSpotFine;
		} else if (spot.getClass() == SmallSpot.class) {
			ticketCost = SmallSpotCost;
			fine = SmallSpotFine;
		} else if (spot.getClass() == TwoWheelerSpot.class) {
			ticketCost = TwoWheelerSpotCost;
			fine = TwoWheelerSpotFine;
		}

		System.out.printf(
				"  Ticket Number : %d \n  Cost : %d Rs \n  Gate Number : %d \n  Vehicle Number : %s \n  Floor : %d \n  Spot Number : %d \n  Issue Date : %s \n  Issue Time : %s",
				ticket.getId(), ticketCost, ticket.getGateId(), vehicleNumber, ticket.getFloorId(), ticket.getSpotid(),
				new Date(millis).toString(), new Time(millis).toString());

		System.out.printf(
				"\n\n  Note: This Ticket is valid for 4 Hours if Stay is Extended You will have to pay a fine of Rs %d per Hour ",
				fine);
		System.out.println(
				"\n***********************************************************************************************************************\n\n");
	}

	public void createTicket() {
		scanner.nextLine();
		System.out.println(
				"Please Confirm if you want to proceed by pressing y or yes else press any other key to abort");
		String selection = scanner.nextLine();
		if (selection.toLowerCase().equals("y") || selection.toLowerCase().equals("yes")) {
			logger.info(" User is trying to create a Ticket ");
			Spot spot = getAvailableSpot();
			if (spot.getId() != 0) {
				System.out.println(
						"\nPlease Confirm if You want to occupy spot by pressing y\n Press any other key to abort");
				scanner.nextLine();
				String choice = scanner.nextLine();
				if (choice.toLowerCase().equals("y") || choice.toLowerCase().equals("yes")) {

					int personId = addPerson(false);
					Vehicle vehicle = addVehicle(personId, false);

					Ticket ticket = new Ticket();
					System.out.println("enter Gate No");
					ticket.setGateId(scanner.nextInt());
					ticket.setVehicleId(vehicle.getId());
					ticket.setFloorId(spot.getFloorId());
					ticket.setSpotid(spot.getId());
					long millis = System.currentTimeMillis();
					ticket.setIssueTime(new Timestamp(millis));

					Element<Ticket> element = new Element<Ticket>();
					element.addElement(ticket.getGateId());
					element.addElement(ticket.getVehicleId());
					element.addElement(ticket.getFloorId());
					element.addElement(ticket.getSpotid());
					element.addElement(ticket.getIssueTime());
					occupySpot(spot, vehicle.getId());
					int ticketID;
					try {
						ticketID = repository.insertTicket(element);
						ticket.setId(ticketID);
						logger.info(" Ticket {} creation Successfull ", ticket.getId());

					} catch (ParkingException e) {
						logger.error("Error While Creating Ticket {}", e);
						System.err.println(e.getMessage());
					}
					printTicketDetails(ticket, vehicle.getVehicleNo(), spot);
				}
			}
		}
	}

	public void addfloor() {
		scanner.nextLine();
		System.out.println(
				"Please Confirm if you want to proceed by pressing y or yes else press any other key to abort");
		String selection = scanner.nextLine();
		if (selection.toLowerCase().equals("y") || selection.toLowerCase().equals("yes")) {
			logger.info(" User is trying to Add a Floor ");

			Floor floor = new Floor();
			System.out.println("Enter Floor No : ");
			floor.setId(scanner.nextInt());
			System.out.println(
					"Please Select One of the Following Choices to Set Spot Status \n 1. Active    2. InActive");
			boolean status = true;
			while (status) {
				int choice = scanner.nextInt();
				switch (choice) {
				case 1:
					floor.setStatus(Status.Active.toString());
					status = false;
					break;
				case 2:
					floor.setStatus(Status.InActive.toString());
					status = false;
					break;
				default:
					System.out.println("The Choice You Have Selected is Wrong please Select Again");
				}
			}
			floor.setTotalCapacity(0);
			floor.setLargeCapacity(0);
			floor.setSmallCapacity(0);
			floor.setHeavyCapacity(0);
			floor.setTwoWheeelerCapacity(0);

			Element<Floor> element = new Element<Floor>();
			element.addElement(floor.getId());
			element.addElement(floor.getTotalCapacity());
			element.addElement(floor.getTwoWheeelerCapacity());
			element.addElement(floor.getSmallCapacity());
			element.addElement(floor.getLargeCapacity());
			element.addElement(floor.getHeavyCapacity());
			element.addElement(floor.getStatus());
			int floorId;
			try {
				floorId = repository.insertFloor(element);
				if (floorId >= 0) {
					System.out.printf("Floor %d Added SuccesFully", floor.getId());
					logger.info("Floor {} Addedd SuccesFully", floor.getId());
				}
			} catch (ParkingException e) {
				logger.error("Error Occured While Adding Floor {}", e);
				System.err.println(e.getMessage());
			}
		}
	}

	public Vehicle addVehicle(int personId, boolean isCalledByUser) {

		if (isCalledByUser) {
			scanner.nextLine();
			System.out.println(
					"Please Confirm if you want to proceed by pressing y or yes else press any other key to abort");
			String selection = scanner.nextLine();
			if (!selection.toLowerCase().equals("y") && !selection.toLowerCase().equals("yes")) {
				return null;
			}
		}

		logger.info("User is trying to create a vehicle");
		scanner.nextLine();
		Vehicle vehicle = new Vehicle();
		System.out.println("\nEnter Vehicle Number");
		vehicle.setVehicleNo(scanner.nextLine());

		int vehicleId = 0;
		try {
			vehicleId = repository.checkVehicle(vehicle);
		} catch (ParkingException e1) {
			logger.error("Error While Checking if vehicle is already Present {} ", e1);
			System.out.println(e1.getMessage());
		}

		if (vehicleId == 0) {
			logger.info("Vehicle Id {} entered is not a old vehicle so creating a new vehicle", vehicle.getVehicleNo());
			System.out.println("Enter Vehicle Type");
			vehicle.setType(scanner.nextLine());
			if (personId == 0) {
				System.out.println("Enter Person Id");
				personId = scanner.nextInt();
			}
			vehicle.setPersonId(personId);

			Element<Vehicle> element = new Element<Vehicle>();
			element.addElement(vehicle.getVehicleNo());
			element.addElement(vehicle.getType());
			element.addElement(vehicle.getPersonId());

			try {
				vehicleId = repository.insertVehicle(element);
				logger.info("Vehicle {} added successfully in database", vehicle.getVehicleNo());
				vehicle.setId(vehicleId);
			} catch (ParkingException e) {
				logger.error("Exception occured while inserting vehicle into database {}", e);
				System.err.println(e.getMessage());
			}
		} else {
			vehicle.setId(vehicleId);
			try {
				repository.updateVehicle(vehicleId, personId);
				logger.info("vehicle No {} is a old vehicle and updating of person id {} succesfull in database",
						vehicle.getVehicleNo(), personId);
			} catch (ParkingException e) {
				logger.error("Error While Updating vehicle Information {}", e);
				System.err.println(e.getMessage());
			}
		}
		return vehicle;
	}

	public void addSpot() {
		scanner.nextLine();
		System.out.println(
				"Please Confirm if you want to proceed by pressing y or yes else press any other key to abort");
		String selection = scanner.nextLine();
		if (selection.toLowerCase().equals("y") || selection.toLowerCase().equals("yes")) {

			logger.info("User is Trying to add a new Spot");
			System.out.println(
					"Select the Spot Type you Want To Add\n\n 1 -- Twowheeler\t2-- Small\t3-- Large\t4 -- Heavy");

			Spot spot = null;
			SpotType spotType = null;
			String floorColumnName = "";
			boolean status = true;
			while (status) {
				int choice = scanner.nextInt();
				switch (choice) {
				case 1:
					spot = new TwoWheelerSpot();
					spotType = SpotType.TwoWheeler;
					floorColumnName = "twoWheelerCapacity";
					status = false;
					break;
				case 2:
					spot = new SmallSpot();
					spotType = SpotType.Small;
					floorColumnName = "smallCapacity";
					status = false;
					break;
				case 3:
					spot = new LargeSpot();
					spotType = SpotType.Large;
					floorColumnName = "largeCapacity";
					status = false;
					break;
				case 4:
					spot = new HeavySpot();
					spotType = SpotType.Heavy;
					floorColumnName = "heavyCapacity";
					status = false;
					break;
				default:
					System.out.println("Wrong Choice Entered Please Enter Choice Again");
				}
			}
			System.out.println("Enter Spot ID : ");
			spot.setId(scanner.nextInt());
			System.out.println("Enter Floor Id");
			spot.setFloorId(scanner.nextInt());
			spot.setAvailability(true);

			System.out.println(
					"Please Select One of the Following Choices to Set Spot Status \n 1. Active    2. InActive");
			status = true;
			while (status) {
				int choice = scanner.nextInt();
				choice = scanner.nextInt();
				switch (choice) {
				case 1:
					spot.setStatus(Status.Active.toString());
					status = false;
					break;
				case 2:
					spot.setStatus(Status.InActive.toString());
					status = false;
					break;
				default:
					System.out.println("Wrong Choice Entered Please Enter Choice Again");
				}
			}

			Element<Spot> element = new Element<Spot>();
			element.addElement(spot.getId());
			element.addElement(spot.getFloorId());
			element.addElement(spotType.toString());
			element.addElement(spot.getAvailability());
			element.addElement(spot.getStatus());
			try {
				repository.insertSpot(element);
				repository.updateSpotCountAtFloor(spot.getFloorId(), floorColumnName, +1);

				logger.info("New Spot {} at Floor {} added succesfully", spot.getId(), spot.getFloorId());

			} catch (ParkingException e) {
				logger.error("Exception as {}", e);
				System.err.println(e.getMessage());
			}
		}
	}

	public void removeGate() {
		scanner.nextLine();
		System.out.println(
				"Please Confirm if you want to proceed by pressing y or yes else press any other key to abort");
		String selection = scanner.nextLine();
		if (selection.toLowerCase().equals("y") || selection.toLowerCase().equals("yes")) {
			System.out.println("Enter Gate No to Remove :");
			int gateNo = scanner.nextInt();

			System.out.printf(
					"\nPlease Confirm if you want to delete Gate %d  By pressing Y else press Any Other Key to Abort \n",
					gateNo);
			scanner.nextLine();
			selection = scanner.nextLine();

			if (selection.toLowerCase().equals("y") || selection.toLowerCase().equals("yes")) {
				Element<Gate> element = new Element<Gate>();
				element.addElement("id");
				element.addElement(gateNo);
				try {
					boolean bool = repository.deleteGate(element);
					if (bool)
						System.out.printf("\ngate %d removed successfully\n", gateNo);
				} catch (ParkingException e) {
					logger.error("Error While Removing Gate {}", e);
					System.err.println(e.getMessage());
				}
			}
		}
	}

	public void removeVehicle() {
		scanner.nextLine();
		System.out.println(
				"Please Confirm if you want to proceed by pressing y or yes else press any other key to abort");
		String selection = scanner.nextLine();
		if (selection.toLowerCase().equals("y") || selection.toLowerCase().equals("yes")) {
			System.out.println("Enter Vehicle Id to Delete");
			String vehicleId = scanner.nextLine();
			scanner.nextLine();
			System.out.printf(
					"\nPlease Confirm if you want to delete Vehicle %s  By pressing Y press Any Other Key to Abort \n",
					vehicleId);
			selection = scanner.nextLine();

			if (selection.toLowerCase().equals("y") || selection.toLowerCase().equals("yes")) {

				Element<Vehicle> element = new Element<Vehicle>();
				element.addElement("vehicleNo");
				element.addElement(vehicleId);
				try {
					boolean bool = repository.deleteVehicle(element);
					if (bool)
						System.out.printf("\nVehicle %s Removed SuccesFully\n", vehicleId);
				} catch (ParkingException e) {
					logger.error("Error While Removing Vehicle Information {}", e);
					System.err.println(e.getMessage());
				}
			}
		}
	}

	public void removePerson() {
		scanner.nextLine();
		System.out.println(
				"Please Confirm if you want to proceed by pressing y or yes else press any other key to abort");
		String selection = scanner.nextLine();
		if (selection.toLowerCase().equals("y") || selection.toLowerCase().equals("yes")) {
			System.out.println("Enter PersonID to remove");
			String personId = scanner.nextLine();
			scanner.nextLine();
			System.out.printf(
					"\nPlease Confirm if you want to delete Person %s  By pressing Y press Any Other Key to Abort \n",
					personId);
			selection = scanner.nextLine();

			if (selection.toLowerCase().equals("y") || selection.toLowerCase().equals("yes")) {

				Element<Person> element = new Element<Person>();
				element.addElement("personId");
				element.addElement(personId);
				try {
					boolean bool = repository.deletePerson(element);
					if (bool)
						System.out.printf("\nPerson %s Deleted SuccesFully\n", personId);
				} catch (ParkingException e) {
					logger.error("Error While Removing Person Information {} ", e);
					System.err.println(e.getMessage());
				}
			}
		}
	}

	public void removeFloor() {
		scanner.nextLine();
		System.out.println(
				"Please Confirm if you want to proceed by pressing y or yes else press any other key to abort");
		String selection = scanner.nextLine();
		if (selection.toLowerCase().equals("y") || selection.toLowerCase().equals("yes")) {
			System.out.println("Enter the Floor No that You Want to remove");
			int floorId = scanner.nextInt();

			System.out.printf(
					"\nPlease Confirm if you want to delete Floor %d  By pressing Y press Any Other Key to Abort \n Removing the floor will delete all the spots belonging to that floor",
					floorId);
			scanner.nextLine();
			selection = scanner.nextLine();

			if (selection.toLowerCase().equals("y") || selection.toLowerCase().equals("yes")) {

				Element<Floor> element = new Element<Floor>();
				element.addElement("id");
				element.addElement(floorId);
				try {
					boolean bool = repository.deleteFloor(element);
					if (bool)
						System.out.printf("\nFloor %d Deleted SuccesFully\n", floorId);
				} catch (ParkingException e) {
					logger.error("Error While Removing Floor ", e);
					System.err.println(e.getMessage());
				}
			}
		}
	}

	public void removeSpot() {
		scanner.nextLine();
		System.out.println(
				"Please Confirm if you want to proceed by pressing y or yes else press any other key to abort");
		String selection = scanner.nextLine();
		if (selection.toLowerCase().equals("y") || selection.toLowerCase().equals("yes")) {
			System.out.println("Enter the floorId whose Spot u want to remove");
			int floorId = scanner.nextInt();
			System.out.println("Enter the Spot No you want to remove : ");
			int spotNo = scanner.nextInt();
			scanner.nextLine();
			System.out.printf(
					"\nPlease Confirm if you want to delete spot %d at Floor %d  By pressing Y press Any Other Key to Abort \n",
					spotNo, floorId);
			selection = scanner.nextLine();

			if (selection.toLowerCase().equals("y") || selection.toLowerCase().equals("yes")) {
				Element<Spot> element = new Element<Spot>();
				element.addElement("floorId");
				element.addElement(floorId);
				element.addElement("id");
				element.addElement(spotNo);
				try {
					boolean bool = repository.deleteSpot(element);
					if (bool)
						System.out.printf("Spot %d at Floor %d deleted SuccesFully", spotNo, floorId);

				} catch (ParkingException e) {
					logger.error("Error While Removing Spot ", e);
					System.err.println(e.getMessage());
				}
			}
		}
	}

	private Spot getAvailableSpot() {
		System.out.println(
				"\n***********************************************************************************************************************************");
		System.out.println("\nSelect the Spot Type You Want \n\n 1 -- Twowheeler\t2-- Small\t3-- Large\t4 -- Heavy");
		SpotType spotType = null;
		Spot spot = null;
		Boolean status = true;
		while (status) {
			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				spot = new TwoWheelerSpot();
				spotType = SpotType.TwoWheeler;
				status = false;
				break;
			case 2:
				spot = new SmallSpot();
				spotType = SpotType.Small;
				status = false;
				break;
			case 3:
				spot = new LargeSpot();
				spotType = SpotType.Large;
				status = false;
				break;
			case 4:
				spot = new HeavySpot();
				spotType = SpotType.Heavy;
				status = false;
				break;
			default:
				System.out.println("Wrong Choice Entered Please Enter Choice Again");
			}
		}
		Spot updatedSpot = null;
		try {
			updatedSpot = repository.availableSpot(spotType, spot);
		} catch (ParkingException e) {
			logger.error("Error While Obtaining available spot from database  ", e);
			System.err.println(e.getMessage());
		}

		if (updatedSpot.getId() != 0) {
			System.out.printf("\nParking Spot %d at Floor %d  is available for Type %s\n", updatedSpot.getId(),
					updatedSpot.getFloorId(), spotType.toString());
		} else {
			System.out.println("Desired Spot Not Available");
		}
		System.out.println(
				"\n***********************************************************************************************************************************");
		return updatedSpot;
	}

	public void checkSpotAvailability() {
		scanner.nextLine();
		System.out.println(
				"Please Confirm if you want to proceed by pressing y or yes else press any other key to abort");
		String selection = scanner.nextLine();
		if (selection.toLowerCase().equals("y") || selection.toLowerCase().equals("yes")) {
			getAvailableSpot();
		}
	}

	private void occupySpot(Spot spot, int vehicleId) {
		logger.info("User Trying to occupy spot");

		if (spot.getId() != 0) {
			Boolean b = null;
			try {
				b = repository.occupySpot(spot.getId(), spot.getFloorId(), vehicleId);
			} catch (ParkingException e) {
				logger.error("Error While Occupying Spot  ", e);
				System.err.println(e.getMessage());
			}
			if (!b) {
				logger.info("Spot {} at floor {} is occupied Succesfully", spot.getId(), spot.getFloorId());
				System.out.println("Spot Cant Be Occupied Now !!! Please Check ");
			}
		} else {
			System.out.println("The Spot you selected is not Available");
		}
	}

	public void releaseSpot() {
		scanner.nextLine();
		System.out.println(
				"Please Confirm if you want to proceed by pressing y or yes else press any other key to abort");
		String selection = scanner.nextLine();
		if (selection.toLowerCase().equals("y") || selection.toLowerCase().equals("yes")) {
			logger.info("User Trying to release spot");
			System.out.println("\nEnter Spot Id : ");
			int spotId = scanner.nextInt();
			System.out.println("Enter Floor Id : ");
			int floorId = scanner.nextInt();

			Boolean b = true;
			try {
				b = repository.releaseSpot(spotId, floorId, 0);
			} catch (ParkingException e) {
				logger.error("Error Occured While Releasing Spot  ", e);
				System.err.println(e.getMessage());
			}
			if (b) {
				logger.info("Spot {} at floor {} is released Succesfully", spotId, floorId);
				System.out.printf("\nSpot %d at floor %d is Released Succesfully\n", spotId, floorId);
			} else
				System.out.println("Spot Cant Be Released Now !!! Please Check ");
		}
	}

	public void printSystemInformation() {
		logger.info("Printing System Information");
		System.out.println(
				"\n***********************************************************************************************************************************");
		ResultSet resultSet = null;
		try {
			resultSet = repository.getFloors();
		} catch (ParkingException e1) {
			logger.error("Error While Obtaining Floors From Database  ", e1);
			System.out.println(e1.getMessage());
		}
		try {
			while (resultSet.next()) {
				int floorId = resultSet.getInt(1);
				System.out.printf("At Floor %d\n\n", floorId);
				EnumSet.allOf(SpotType.class).forEach(spot -> {
					System.out.printf("For Spot Type : %s ", spot.toString());
					try {
						int totalSpots = repository.totalSpotsAtFloor(floorId, spot.toString());
						int freeSpots = repository.getSpotInformation(floorId, spot.toString(), true);
						int occupiedSpots = repository.getSpotInformation(floorId, spot.toString(), false);
						int availableSpots = freeSpots + occupiedSpots;
						System.out.printf(
								"\nTotal Spots : %d    Available Spots : %d    Free Spots : %d    Occupied Spots : %d\n\n",
								totalSpots, availableSpots, freeSpots, occupiedSpots);
					} catch (ParkingException e) {
						logger.error("Error While Obtaining Information about spots from database  ", e);
					}
				});
			}
		} catch (Exception e) {
			logger.error("Error While Printing System Information : ", e);
		}
		System.out.println(
				"\n***********************************************************************************************************************************");
	}

	public void changeFloorStatus() {
		scanner.nextLine();
		System.out.println(
				"Please Confirm if you want to proceed by pressing y or yes else press any other key to abort");
		String selection = scanner.nextLine();
		if (selection.toLowerCase().equals("y") || selection.toLowerCase().equals("yes")) {
			logger.info("user Trying to update Floor Status");
			System.out.println("Enter Floor Id whose Status you want To update ");
			int id = scanner.nextInt();
			System.out.println(
					"Please Select One of the Following Choices to Set Floor Status \n 1. Active    2. InActive");
			Boolean bool = true;
			String status = null;
			while (bool) {
				int choice = scanner.nextInt();
				switch (choice) {
				case 1:
					status = Status.Active.toString();
					bool = false;
					break;
				case 2:
					status = Status.InActive.toString();
					bool = false;
					break;
				default:
					System.out.println("Wrong Choice Entered Please Enter Choice Again");
				}
			}
			try {
				repository.updateStatusFloor(id, status);
				logger.info("Floor {} activated successfully", id);
				System.out.printf("\nFloor %d Updated SuccessFully\n", id);
			} catch (ParkingException e) {
				logger.error("Error while Updating Floor {} Status ", id, e);
				System.err.println(e.getMessage());
			}
		}
	}

	public void changeGateStatus() {
		scanner.nextLine();
		System.out.println(
				"Please Confirm if you want to proceed by pressing y or yes else press any other key to abort");
		String selection = scanner.nextLine();
		if (selection.toLowerCase().equals("y") || selection.toLowerCase().equals("yes")) {
			logger.info("user Trying to update Gate Status");
			System.out.println("Enter Gate Id you whose Status You want To update ");
			int id = scanner.nextInt();
			System.out.println(
					"Please Select One of the Following Choices to Set Gate Status \n 1. Active    2. InActive");
			Boolean bool = true;
			String status = null;
			while (bool) {
				int choice = scanner.nextInt();
				switch (choice) {
				case 1:
					status = Status.Active.toString();
					bool = false;
					break;
				case 2:
					status = Status.InActive.toString();
					bool = false;
					break;
				default:
					System.out.println("The Choice You Have Selected is Wrong please Select Again");
				}
			}
			try {
				repository.updateStatusGate(id, status);
				logger.info("status of Gate {} updated successfully", id);
				System.out.printf("\n status of Gate %d Updated SuccessFully\n", id);
			} catch (ParkingException e) {
				logger.error("Error while Updating Floor {} Status ", id, e);
				System.err.println(e.getMessage());
			}
		}
	}

	public void changeSpotStatus() {
		scanner.nextLine();
		System.out.println(
				"Please Confirm if you want to proceed by pressing y or yes else press any other key to abort");
		String selection = scanner.nextLine();
		if (selection.toLowerCase().equals("y") || selection.toLowerCase().equals("yes")) {
			logger.info("user Trying to change status of Spot");

			System.out.println("Enter Floor of the Spot whose Status You want To update  ");
			int floorId = scanner.nextInt();
			System.out.println("Enter Spot Number whose Status You want To update ");
			int id = scanner.nextInt();

			System.out.println(
					"\nPlease Select One of the Following Choices to Set Floor Status as \n 1. Active    2. InActive");
			Boolean bool = true;
			String status = null;
			while (bool) {
				int choice = scanner.nextInt();
				switch (choice) {
				case 1:
					status = Status.Active.toString();
					// System.out.println("\n\n");
					bool = false;
					break;
				case 2:
					status = Status.InActive.toString();
					bool = false;
					break;
				default:
					System.out.println("\nThe Choice You Have Selected is Wrong please Select Again\n");
				}
			}
			try {
				repository.updateStatusSpot(id, floorId, status);
				logger.info("status of spot {} at floor {} updated successfully", id, floorId);
				System.out.printf("\nstatus of spot %d at floor %d Updated SuccessFully", id, floorId);
			} catch (ParkingException e) {
				logger.error("Error while Updating Floor {} Status ", id, e);
				System.err.println(e.getMessage());
			}
		}
	}
}
