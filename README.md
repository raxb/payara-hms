## Maiden Payara Hackathon Project Template

This is the base project for the maiden edition of the Payara Hackathon. It is a single module maven project that depends on Jakarta EE and Eclipse MicroProfile as the main APIs. No other third party library is allowed within the scope of the competition. Arquillian and Mockito are already set up. 

### Runtime
The project uses Payara Server 6 Community running on JDK 17, pulled in through the Dockerfile

### Running the project
The project can be packaged as a docker image using the bundled Dockerfile. It also has a docker-compose file that comes with MySQL database should you be interested in setting up a persistent storage for your application (not requirement though). By default, Jakarta Persistence will use an in-memory database from the bundled Payara Server. 


## About
Payara Hospital Management System

A one place solution (one page to run them all) for the hospital staff to monitor the status of hospital operations.
As a hospital staff, who is having an access to the HMS dashboard will be able to obtain details on the below,
1. Patients with an appointment for the current day
	a. rest client to get the details of the patient given patient_id 
	b. rest client to save patient details with JSON request
	c. rest client to get appointments for any given date
	d. rest client to patch the upcoming appointment date for given patient_id in the request URL - so as to rescheduling the appointment

2. Pharmacy Inventory with item price and quantity
	a. rest client to search feature with item name (as %item name%) and subscribe  
	b. ability to subscribe with registered email for quantity change(availability) for each pharmacy item
	c. scheduled notification to periodically querying and sending mail notification
	d. rest client for adding a new item/updating the item quantity such in turn triggering a scheduled notification for the item quantity updated for the subscribers
3. Ward Information
	a. shows available and booked wards
	b. book a ward to a registered patient with email and name combination
	
	
Technicals:
1. Rest endpoints
	a. Path variables
	b. PathParams
	c. Media type produces and consumes
	d. Constraint validation
	e. CDI Injection
2. Service
	a. Transactional
	b. Persistence context
	c. Application scoping
	d. Request scoping
	e. Model named for JSF access
	f. Event fire and observable persistence
	g. Scheduler for periodic notification based on DB value
	h. Around interceptor proxy for logging and time for processing
	