package fish.payara.jumpstartjee;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class WardBookedDetailsModel {

	private String patientName;
	private String patientEmail;
	private WardType wardType;
	private Date bookedFromDate;
	private List<WardBookedDetailsModel> bookingDetails;

	@Inject
	private WardService wardService;

	public WardBookedDetailsModel(String patientName, String patientEmail, WardType wardType, Date bookedFromDate) {
		super();
		this.patientName = patientName;
		this.patientEmail = patientEmail;
		this.wardType = wardType;
		this.bookedFromDate = bookedFromDate;
	}

	@PostConstruct
	public void init() {
		
	}

	public String getPatientName() {
		return patientName;
	}

	public String getPatientEmail() {
		return patientEmail;
	}

	public WardType getWardType() {
		return wardType;
	}

	public Date getBookedFromDate() {
		return bookedFromDate;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	public void setWardType(WardType wardType) {
		this.wardType = wardType;
	}

	public void setBookedFromDate(Date bookedFromDate) {
		this.bookedFromDate = bookedFromDate;
	}

	public List<WardBookedDetailsModel> getBookingDetails() {
		return bookingDetails;
	}
	
	public void refresh() {
		bookingDetails = wardService
				.getAllWardDetails().stream().map(ward -> new WardBookedDetailsModel(ward.getPatientName(),
						ward.getPatientEmail(), ward.getWardType(), ward.getBookedFromDate()))
				.collect(Collectors.toList());
	}

}
