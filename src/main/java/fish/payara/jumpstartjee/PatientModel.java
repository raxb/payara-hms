package fish.payara.jumpstartjee;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class PatientModel {

	private String patientName;
	private Gender gender;
	private int age;
	private String email;
	@Nullable
	private Date lastVisited;
	private List<PatientModel> result;

	@Inject
	private PatientDetailService patientDetailService;

	@PostConstruct
	public void init() {

	}

	public PatientModel() {
	}

	public PatientModel(String patientName, Gender gender, int age, String email, Date lastVisited) {
		this.patientName = patientName;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.lastVisited = lastVisited;
	}

	public List<PatientModel> getPatientsForToday() {
		result = appointmentsForToday().stream().map(p -> new PatientModel(p.getFirstname() + " " + p.getLastname(),
				p.getGender(), p.getAge(), p.getEmail(), p.getLastAppointment())).collect(Collectors.toList());
		return result;
	}

	private List<PatientEntity> appointmentsForToday() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date today = new Date();
		Date eod = Date.from(today.toInstant().plus(1, ChronoUnit.DAYS));

		return patientDetailService.patientsAppointmentForDay(today, eod);
	}

	public List<PatientModel> getResult() {
		return result;
	}

	public String getPatientName() {
		return patientName;
	}

	public Gender getGender() {
		return gender;
	}

	public int getAge() {
		return age;
	}

	public String getEmail() {
		return email;
	}

	public Date getLastVisited() {
		return lastVisited;
	}

}
