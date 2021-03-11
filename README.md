                                                        Parking System
                                                        
                                                        
This is a project to manage a parking lot. It has an  ticketing system that facilitates the interactions between the user and the parking system.

Project Detailing & Use Cases
    •	A parking lot can hold up to 'n' cars at any given point in time
    •	A parking lot has multiple floors and each floor has its own spots available
    •	There are four types of parking spot in the system i.e Two Wheeler , Small , Large , Heavy
    •	Create an automated ticketing system that allows the use of parking lot
    •	When a car enters the parking lot, a ticket issued is issued to the driver
    •	Before creating the ticket information about the user and the vehicle is asked from the user i.e vehicles license plate no, users id no etc 
    •	If the user is a regular customer then his data will be automatically fetched from database similarly for vehicle too
    •	At the exit the customer returns the ticket which then marks the slot they were using as being available
    •	The Ticket provided to the user contains information about :
        o	Registration numbers of the car
        o	Slot number and floor in which the car is parked 
        o	Ticket cost 
        o	Time at which ticket was issued
    •	The mode of input for the project is command prompt and output will also be shown on the command prompt
    •	Also have provided various features such as :
        o	Have done mobile number validation using regular expressions 
        o	Whenever a spot is added or deleted the corresponding total spot count and spot count of the added or deleted type will be automatically increased or deleted
        o	If any floor is deleted the corresponding spots belonging to that floor will also be deleted
        o	If the status of any floor is updated the status of that corresponding spots is also updated


Technologies used in the project
    1.	Maven project
    2.	Mysql Database
    3.	PlSQL Triggers
    4.	Regular expressions in java
    5.	Logging using log4j
    6.	Generics
    7.	JDBC 
    8.	Custom Exception Handling
    9.	Java Collections and Time API

