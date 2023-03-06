package fish.payara.jumpstartjee.hms.patient;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import fish.payara.jumpstartjee.Gender;
import fish.payara.jumpstartjee.ValidPatient;
import jakarta.annotation.Nullable;
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
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

@ValidPatient
@Entity
@Table(name = "PatientEntity")
public class PatientEntity implements Serializable {

	private static final long serialVersionUID = 4589520205013518575L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PATIENT_ID")
	private Long patient_id;
	
	@NotEmpty(message ="Patient First name must not be empty")
	@Column(name = "FIRST_NAME")
	private String firstname;

	@NotEmpty(message ="Patient Last name must not be empty")
	@Column(name = "LAST_NAME")
	private String lastname;

	@Enumerated(EnumType.STRING)
	@Column(name = "GENDER")
	private Gender gender;

	@Positive(message ="Patient Age must be greater than 0")
	@Column(name = "AGE")
	private Integer age;

	@Column(name = "EMAIL")
	private String email;

	@NotEmpty(message ="Address Line 1 must not be empty")
	@Column(name = "ADDRESS_LINE1")
	private String addressLine1;
	@Nullable
	@Column(name = "ADDRESS_LINE2")
	private String addressLine2;
	@NotEmpty(message ="City must not be empty")
	@Column(name = "CITY")
	private String city;
	@NotEmpty(message ="State must not be empty")
	@Column(name = "STATE")
	private String state;
	@NotEmpty(message ="Country must not be empty")
	@Column(name = "COUNTRY")
	private String country;
	@NotEmpty(message ="ZipCode must not be empty")
	@Column(name = "ZIP_CODE")
	private String zipCode;

	@Nullable
	@Column(name = "LAST_APPOINTMENT")
	@Past(message ="Last appointment date cannot be a future date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
	private Date lastAppointment;

	@Nullable
	@Column(name = "UPCOMING_APPOINTMENT")
	@FutureOrPresent(message ="Upcoming appointment must be present or future date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
	private Date upcomingAppointment;

	public Long getPatient_id() {
		return patient_id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Date getLastAppointment() {
		return lastAppointment;
	}

	public void setLastAppointment(Date lastAppointment) {
		this.lastAppointment = lastAppointment;
	}

	public Date getUpcomingAppointment() {
		return upcomingAppointment;
	}

	public void setUpcomingAppointment(Date upcomingAppointment) {
		this.upcomingAppointment = upcomingAppointment;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(age, email, firstname, gender, lastname, patient_id, upcomingAppointment);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatientEntity other = (PatientEntity) obj;
		return Objects.equals(age, other.age) && Objects.equals(email, other.email)
				&& Objects.equals(firstname, other.firstname) && gender == other.gender
				&& Objects.equals(lastname, other.lastname) && Objects.equals(patient_id, other.patient_id)
				&& Objects.equals(upcomingAppointment, other.upcomingAppointment);
	}

	@Override
	public String toString() {
		return "PatientEntity [patient_id=" + patient_id + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", gender=" + gender + ", age=" + age + ", email=" + email + ", addressLine1=" + addressLine1
				+ ", addressLine2=" + addressLine2 + ", city=" + city + ", state=" + state + ", country=" + country
				+ ", zipCode=" + zipCode + ", lastAppointment=" + lastAppointment + ", upcomingAppointment="
				+ upcomingAppointment + "]";
	}

}
