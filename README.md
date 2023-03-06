# About
**Payara Hospital Management System (HMS)**

## Runtime
The project uses Payara Server 6 Community running on JDK 11.

## Running the project
Perform _mvn clean package_, and start the payara server and deploy the application.  
The application can be accessed via [http://localhost:8080/jee-jumpstart/index.xhtml](http://localhost:8080/jee-jumpstart/index.xhtml) from browser
and [http://localhost:8080/jee-jumpstart/*](http://localhost:8080/jee-jumpstart/*) correspondingly for the REST endpoint (please refer  _/payara-hms/commands.txt_  )

---

A one place solution (one page to run them all) for the hospital staff to monitor the status of hospital operations.  
As a hospital staff, who is having an access to the HMS dashboard will be able to obtain details on the patient appointments, pharmacy stock details, notification on stock availability, ward booking and associate cost calculation.

---

Single POM module structured and packaged with respect to the domain-models available in a typical hospital system, the data is pre-created and loaded to  _in memory database H2_  as part of the application start up from the script at  _/payara-hms/src/main/resources/META-INF/defaultdata.sql_   
Also, refer to ( _/payara-hms/commands.txt_ ) file in the project module for the REST endpoint details and corresponding request types with JSON body.

---

## 1. Patient Module
(necessary MediaTypes are specified for each of the Path provided REST endpoints for Producing and Consuming respectively on the resource under query)
   1. rest client to get the details of the patient given  _PathParam_  typed patient_id from the pre-loaded data setup<br/><br/>
   2. rest client to save patient details with JSON request and validation on the entity with custom annotation  _fish.payara.jumpstartjee.ValidPatient_  enforcing additional constraint validations via  _fish.payara.jumpstartjee.hms.patient.PatientDataChecks_  (patient age between 0 and 150, with a valid email id syntax)<br/><br/>
   3. rest client to get all patients registered in the HMS<br/><br/>
   4. rest client to get appointments for any given date utilizing  _PathParams_  for binding the URL value as an object for processing<br/><br/>
   5. rest client to patch the upcoming appointment date for given patient_id in the request URL - so as to do rescheduling the upcoming appointment (since the endpoint is exposed the PATCH request is required to have the correct patient_id registered in the HMS to opt for rescheduling, else exception as  _"Incorrect patient details, please ensure you sent the correct patient_id in the requests"_  )
        - Further the PATCH endpoint is a  _publisher_ of the _fish.payara.jumpstartjee.AddAppointmentEvent.AddAppointmentEvent(PatientEntity)_  and the corresponding _subscriber_   _fish.payara.jumpstartjee.hms.patient.PatientDetailService.updateAppointment(AddAppointmentEvent)_  observes and persists to the entity  _PatientEntity_
        - **Room for improvement** - subsequently can add logic to trigger an email notification for the reschedule on the registered patient email id<br/><br/>
   6. _fish.payara.jumpstartjee.hms.patient.PatientDetailService_  is the  _ApplicationScoped_  Service layer and interacts with the database managing the transaction on the persistence context, commit on success else rollback the transaction process<br/><br/>
   7. The service methods are intercepted with LoggedAndTimed to monitor and debug the application data access and processing via observability integrations providing room for further optimization and alerting on spikes<br/><br/>
   8. _fish.payara.jumpstartjee.hms.patient.PatientModel_   is the object bean for binding the data to and fro the JSF components<br/><br/>
   9. _/payara-hms/src/main/webapp/index.xhtml_  - Today's Appointments binds to the  _fish.payara.jumpstartjee.hms.patient.PatientModel.getPatientsForToday()_  and renders the appoint for the day. 
   **Room for improvement** is commented in xhtml, with a date field against each patient appointment for rescheduling and invoking the event fire and observed for persisting the updated appointment data (referenced to **point .5**) from the dashboard.  
     
---

## 2. Pharmacy Inventory with Notification 
(necessary MediaTypes are specified for each of the Path provided REST endpoints for Producing and Consuming respectively on the resource under query)
   1. rest client to search pharmacy with item name (as  _%item name%_ ) given PathParam typed item name from the pre-loaded data setup.
   **Room for improvement** - can return Response such that the results can be added to body and return appropriate HTTP status codes by building the body. (commented out)<br/><br/>
   2. rest client to add item to pharmacy ie., can also be used to update existing item price and quantity
         - This POST endpoint is a  _publisher_  of the  _fish.payara.jumpstartjee.AddItemEvent.AddItemEvent(PharmacyEntity)_  and the corresponding  _subscriber fish.payara.jumpstartjee.NotificationService.updateNotificationOnItemAvailability(AddItemEvent)_  observers and updates the registered notification for the item, such that on change of the item notification entity will be updated (for those who were registered for notification on that item)<br/><br/>
   3. _fish.payara.jumpstartjee.hms.pharmacy.PharmacyItemService_  is the  _ApplicationScoped_  Service layer and interacts with the database managing the transaction on the persistence context, commit on success else rollback the transaction process<br/><br/>
   4. The service methods are intercepted with LoggedAndTimed to monitor and debug the application data access and processing via observability integrations providing room for further optimization and alerting on spikes<br/><br/>
   5. _fish.payara.jumpstartjee.hms.pharmacy.PharmacyModel_  - object bean for binding the data to and fro the JSF components, created  _PostConstruct_  to get the pharmacy item inventory details -  _fish.payara.jumpstartjee.hms.pharmacy.PharmacyModel.init()_  - binded to pharmacyModelData on load on  _index.xhtml_ <br/><br/>
   6. Pharmacy Inventory displays a search button which is enabled with partial search ie., the staff can enter part of the item name to get the possible results. Rendered via - _fish.payara.jumpstartjee.hms.pharmacy.PharmacyModel.searchForItem()_ <br/><br/>
   7. Also from the inventory we have an option to register for notification on the item quantity or price change, the HMS need to enter only the registered patient's email, else exception will be thrown from the application logs.,
   
    ```
    "2023-03-06T13:29:15.843+0530|WARNING: /index.xhtml @112,24 actionListener="#{pharmacyModel.addToNotification(pharmacyData.itemId)}": java.lang.Exception: 
		There is no patient registered with the emailId sdgd@gdfgd.com
		Please register the patient details!"
		```
    
We can subscribe to notify with a registered patient email id and when we POST (refer **point 2.**) an event will be fired and notification call is observed and triggers system log for notification

```
"2023-03-06T13:30:45.011+0530|INFO: ===============> Sending Mail kent@clark.com to Notify ..." and scheduled notification to periodically querying and sending mail notification
```

And a rest client for adding a new item/updating the item quantity such in turn triggering a scheduled notification for the item quantity updated for the subscribers
		
---

## 3. Notifications Module
   1. For a pharmacy item stock, a registered patient's email ID can be provided to receive notification on item changes - entity to maintain the same _fish.payara.jumpstartjee.hms.notification.ItemNotificationEntity_ <br/><br/>
   2. _fish.payara.jumpstartjee.hms.notification.NotificationScheduler_ - is a Singleton class with a  _scheduler_  specification that executes the method on every 30th minute 5th of second triggering - _fish.payara.jumpstartjee.hms.notification.NotificationScheduler.automaticallyScheduled()_
   **Room for improvement** we can configure a Jakarta Messaging/Mail modules to send actual mail to the intend recipients
   **Room for improvement** also the notification event triggers can be made async with observables on respective entity state changes as subscriptions applied<br/><br/>
   3. Refer **point 2** from Pharmacy Inventory Module for as part of notification flow
       
---

## 4. Ward Booking Information Module 
(necessary MediaTypes are specified for each of the Path provided REST endpoints for Producing and Consuming respectively on the resource under query)
   1. rest client to get all the booked ward details with patient name, email and date occupied.<br/><br/>
   2. _fish.payara.jumpstartjee.hms.ward.WardService_ -  is the  _ApplicationScoped_  Service layer and interacts with the database managing the transaction on the persistence context, commit on success else rollback the transaction process<br/><br/>
   3. The service methods are intercepted with  _LoggedAndTimed_  to monitor and debug the application data access and processing via observability integrations providing room for further optimization and alerting on spikes<br/><br/>
   4. _fish.payara.jumpstartjee.hms.ward.WardBookingModel_ - object bean for binding the data to and fro the JSF components<br/><br/>
   5. _fish.payara.jumpstartjee.hms.ward.WardEntity_ - is the data entity managing the jakarta validations with field column level annotations as well from the _index.xhtml_ via a custom annotation  _fish.payara.jumpstartjee.hms.ward.ValidWardBooking_  enforcing constraint validations on groups  _fish.payara.jumpstartjee.hms.ward.ValidWardBookingGroup_  and validated by checks at   _fish.payara.jumpstartjee.hms.ward.WardBookingDataChecks_ 
   **Room for improvement** - by injecting the patient details service here we can enforce strict validations on booking only for registered patients and avoid dedups on booking the wards<br/><br/>
   6. _/payara-hms/src/main/webapp/index.xhtml_  containing AJAX components for bean validations and grouping<br/><br/>
   7. The Ward Booking also hosts hyperlink to display the details of wards that are booked binded via  _fish.payara.jumpstartjee.hms.ward.WardBookedDetailsModel_  and booked under 3 category., ie. GENERAL, PRIVATE, PRIVATE_PLUS also the page houses an ability to calculate bill for the ward and from date of occupancy refer ( _fish.payara.jumpstartjee.hms.ward.BillingService_ ). 
   **Room for improvement** - can have a print option to get a downloadable PDF for the bill break down respective to each patient.
	
---
	
## Technicals:

    1. Path variables
    2. Path
    3. PathParams
    4. Media type produces and consumes
    5. Constraint validation
    6. CDI Injection
    7. Transactional
    8. Persistence context
    9. Application scoping
    10. Request scoping
    11. Model named for JSF access
    12. Event fire and observable persistence
    13. Scheduler
    14. Around interceptor proxy for logging and time for processing
    15. Event fire and Observers (ObserversAsync)
    16. JPA annotations
    17. Singleton beans
    18. JSF
    19. AJAX Rendering and executions
    20. Binding Named Beans
    21. FacesConverter
    22. Client Side JSF Validations
    23. JSON Bindings
    24. Interceptors
    25. JSON Parsing
    26. Bean validations
    27. Standard Tag Libraries 


---

There are a few open issues, ie., 
like the ajax form command button execution resets the page data,
And no pages for confirmation redirection.
