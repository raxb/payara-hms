package fish.payara.jumpstartjee;

import java.util.Date;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class WardBookingModel {

	private String patientName;
	private String patientEmail;
	private WardType wardType;
	private Date bookedFromDate;
	private List<WardBookingModel> bookingDetails;

	private WardEntity wardEntity = new WardEntity();

	@Inject
	private WardService wardService;

	@PostConstruct
	public void init() {
	}

	public WardEntity getWardEntity() {
		return wardEntity;
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

	public List<WardBookingModel> getBookingDetails() {
		return bookingDetails;
	}

	public void bookWardForPatient() {
		wardService.bookWard(wardEntity);
	}

}
