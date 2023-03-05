package fish.payara.jumpstartjee;

import java.util.Date;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@RequestScoped
@Named
public class WardBookingModel {
	
	private String patientNameForBooking;
	private String patientEmailId;
	private WardType wardType;
	private Date bookedFromDate;
	
	public String getPatientEmailId() {
		return patientEmailId;
	}
	
	public String getPatientNameForBooking() {
		return patientNameForBooking;
	}
	public WardType getWardType() {
		return wardType;
	}
	public Date getBookedFromDate() {
		return bookedFromDate;
	}
	
	

}
