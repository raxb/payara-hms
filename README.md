## Maiden Payara Hackathon Project Template

This is the base project for the maiden edition of the Payara Hackathon. It is a single module maven project that depends on Jakarta EE and Eclipse MicroProfile as the main APIs. No other third party library is allowed within the scope of the competition. Arquillian and Mockito are already set up. 

### Runtime
The project uses Payara Server 6 Community running on JDK 17, pulled in through the Dockerfile

### Running the project
The project can be packaged as a docker image using the bundled Dockerfile. It also has a docker-compose file that comes with MySQL database should you be interested in setting up a persistent storage for your application (not requirement though). By default, Jakarta Persistence will use an in-memory database from the bundled Payara Server. 


## About
Payara Hospital Management System (HMS)

A one place solution (one page to run them all) for the hospital staff to monitor the status of hospital operations.
As a hospital staff, who is having an access to the HMS dashboard will be able to obtain details on the below,

Structured and packaged based on the domain-model in a typical hospital system, the data is pre created and loaded to in memory database H2 as part of the application start up from the script at /payara-hms/src/main/resources/META-INF/defaultdata.sql.
Please refer to (commands.txt) file in the project module for the REST endpoint details and corresponding request type with JSON body

1. Patient (necessary MediaTypes are specified for each of the Path provided REST endpoints for Producing and Consuming respectively on the resource under query)
	a. rest client to get the details of the patient given PathParam typed patient_id from the pre-loaded data setup
	b. rest client to save patient details with JSON request and validation on the entity with custom annotation fish.payara.jumpstartjee.ValidPatient enforcing additional constraint validations via fish.payara.jumpstartjee.hms.patient.PatientDataChecks (patient age between 0 and 150, with a valid email id syntax)
	c. rest client to get all patients registered in the HMS
	d. rest client to get appointments for any given date utilizing PathParams for binding the URL value as an object for processing
	e. rest client to patch the upcoming appointment date for given patient_id in the request URL - so as to do rescheduling the upcoming appointment (since the endpoint is exposed the PATCH request is required to have the correct patient_id registered in the HMS to opt for rescheduling, else exception as "Incorrect patient details, please ensure you sent the correct patient_id in the requests")
		Further the PATCH endpoint is a publisher of the fish.payara.jumpstartjee.AddAppointmentEvent.AddAppointmentEvent(PatientEntity) and the corresponding subscriber fish.payara.jumpstartjee.hms.patient.PatientDetailService.updateAppointment(AddAppointmentEvent) observes and persists to the entity PatientEntity.
		Room for improvement - subsequently can add logic to trigger an email notification for the reschedule on the registered patient email id.
	f. fish.payara.jumpstartjee.hms.patient.PatientDetailService is the ApplicationScoped Service layer and interacts with the database managing the transaction on the persistence context, commit on success else rollback the transaction process.
	g. The service methods are intercepted with LoggedAndTimed to monitor and debug the application data access and processing via observalibility intergrations providing room for further optimization and alerting on spikes.
	h. fish.payara.jumpstartjee.hms.patient.PatientModel is the object bean for binding the data to and fro the JSF components
	i. /payara-hms/src/main/webapp/index.xhtml - Today's Appointments binds to the fish.payara.jumpstartjee.hms.patient.PatientModel.getPatientsForToday() and renders the appoint for the day. Room for improvement is commented in xhtml, with a date field against each patient appointment for rescheduling and invoking the event fire and observed for persisting the updated appointment data (referenced to point .e) from the dashboard. 

2. Pharmacy Inventory with item price and quantity (necessary MediaTypes are specified for each of the Path provided REST endpoints for Producing and Consuming respectively on the resource under query)
	a. rest client to search pharmacy with item name (as %item name%) given PathParam typed item name from the pre-loaded data setup - Room for improvement - can return Response such that the results can be added to body and return appropriate HTTP status codes by building the body. (commented out)
	b. rest client to add item to pharmacy ie., can also be used to update existing item price and quantity.
		This POST endpoint is a publisher of the fish.payara.jumpstartjee.AddItemEvent.AddItemEvent(PharmacyEntity) and the corresponding subscriber fish.payara.jumpstartjee.NotificationService.updateNotificationOnItemAvailability(AddItemEvent) observers and updates the registered notification for the item, such that on change of the item notification entity will be updated (for those who were registered for notification on that item)
	c. fish.payara.jumpstartjee.hms.pharmacy.PharmacyItemService is the ApplicationScoped Service layer and interacts with the database managing the transaction on the persistence context, commit on success else rollback the transaction process.
	d. The service methods are intercepted with LoggedAndTimed to monitor and debug the application data access and processing via observalibility intergrations providing room for further optimization and alerting on spikes.
	e. fish.payara.jumpstartjee.hms.pharmacy.PharmacyModel - object bean for binding the data to and fro the JSF components, created PostConstruct to get the pharmacy item inventory details - fish.payara.jumpstartjee.hms.pharmacy.PharmacyModel.init() - binded to pharmacyModelData on load on index.xhtml
	f. Pharmacy Inventory displays a search button which is enabled with partial search ie., the staff can enter part of the item name to get the possible results. Rendered via - fish.payara.jumpstartjee.hms.pharmacy.PharmacyModel.searchForItem()
	g. Also from the inventory we have an option to register for notification on the item quantity or price change, the HMS need to enter only the registerd patient's email, else exception will be thrown from the application logs., - "2023-03-06T13:29:15.843+0530|WARNING: /index.xhtml @112,24 actionListener="#{pharmacyModel.addToNotification(pharmacyData.itemId)}": java.lang.Exception: 
		There is no patient registered with the emailId sdgd@gdfgd.com
		Please register the patient details!"
	h. We can subscribe to notify with a registered patient email id and when we POST (refer point b.) an event will be fired and notification call is observed and triggers system log for notification "2023-03-06T13:30:45.011+0530|INFO: ===============> Sending Mail kent@clark.com to Notify ..." and scheduled notification to periodically querying and sending mail notification
	i. rest client for adding a new item/updating the item quantity such in turn triggering a scheduled notification for the item quantity updated for the subscribers
	
Notifications
	a. For a pharmacy item stock, a registered patient's email ID can be provided to receive notification on item changes - entity to maintain the same fish.payara.jumpstartjee.hms.notification.ItemNotificationEntity
	b. fish.payara.jumpstartjee.hms.notification.NotificationScheduler - is a Singleton class with a scheduler specification that executes the method on every 30th minute 5th of second triggering - fish.payara.jumpstartjee.hms.notification.NotificationScheduler.automaticallyScheduled() - Room for improvement we can configure a Jakarta Messaging/Mail modules to send actual mail to the intend recepients
	c. Refer point 2.c for as part of notification flow
	
3. Ward Information (necessary MediaTypes are specified for each of the Path provided REST endpoints for Producing and Consuming respectively on the resource under query)
	a. rest client to get all the booked ward details with patient name, email and date occupied.
	b. fish.payara.jumpstartjee.hms.ward.WardService - is the ApplicationScoped Service layer and interacts with the database managing the transaction on the persistence context, commit on success else rollback the transaction process.
	c. The service methods are intercepted with LoggedAndTimed to monitor and debug the application data access and processing via observalibility intergrations providing room for further optimization and alerting on spikes.
	d. fish.payara.jumpstartjee.hms.ward.WardBookingModel - object bean for binding the data to and fro the JSF components
	e. fish.payara.jumpstartjee.hms.ward.WardEntity - is the data entity managing the jakarta validations with field column level annotations as well from the index.xhtml via a custom annotation fish.payara.jumpstartjee.hms.ward.ValidWardBooking enforcing constraint validations on groups fish.payara.jumpstartjee.hms.ward.ValidWardBookingGroup and validated by checks at fish.payara.jumpstartjee.hms.ward.WardBookingDataChecks - Room for improvement - by injecting the patient details service here we can enforce strict validations on booking only for registered patients and avoid dedups on booking the wards.
	f. The Ward Booking also hosts hyperlink to display the details of wards that are booked binded via fish.payara.jumpstartjee.hms.ward.WardBookedDetailsModel and booked 3 category., ie. GENERAL, PRIVATE, PRIVATE_PLUS and the page houses an ability to calculate bill for the ward and from date of occupancy refer (fish.payara.jumpstartjee.hms.ward.BillingService). Room for improvement - can have a print option to get a downloadable PDF for the bill break down respective to each patient.
	
	
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
	