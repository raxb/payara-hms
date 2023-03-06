package fish.payara.jumpstartjee.hms.ward;

import java.io.Serializable;
import java.util.Date;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;

@ValidWardBooking(groups = ValidWardBookingGroup.class)
@Entity
@Table(name = "WardEntity")
public class WardEntity implements Serializable{
	
	private static final long serialVersionUID = -978183045617288207L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "WARD_ID")
	private Long wardId;

	@NotEmpty(message ="Patient name must not be empty")
	@Column(name = "PATIENT_NAME")
	private String patientName;
	
	@NotEmpty(message ="Patient email must not be empty")
	@Email(message="Please provide a valid email address")
	@Column(name = "PATIENT_EMAIL")
	private String patientEmail;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "WARD_TYPE")
	private WardType wardType;
	
	@Column(name = "BOOKED_FROM_DATE")
	@FutureOrPresent(message ="Booking Date must be present or future date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
	private Date bookedFromDate;

	public Long getWardId() {
		return wardId;
	}

	public void setWardId(Long wardId) {
		this.wardId = wardId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientEmail() {
		return patientEmail;
	}

	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	public WardType getWardType() {
		return wardType;
	}

	public void setWardType(WardType wardType) {
		this.wardType = wardType;
	}

	public Date getBookedFromDate() {
		return bookedFromDate;
	}

	public void setBookedFromDate(Date bookedFromDate) {
		this.bookedFromDate = bookedFromDate;
	}
	
	
}
