package fish.payara.jumpstartjee.hms.patient;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import fish.payara.jumpstartjee.hms.utils.AddAppointmentEvent;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class PatientModel {

	private long patient_id;
	private String patientName;
	private Gender gender;
	private int age;
	private String email;
	@Nullable
	private Date lastVisited;
	private Date rescheduleDate;
	private List<PatientModel> result;

	@Inject
	private PatientDetailService patientDetailService;
	
	@Inject
	private Event<AddAppointmentEvent> addAppointmentEvent;

	@PostConstruct
	public void init() {

	}

	public PatientModel() {
	}

	public PatientModel(Long patient_id, String patientName, Gender gender, int age, String email, Date lastVisited) {
		this.patient_id = patient_id;
		this.patientName = patientName;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.lastVisited = lastVisited;
	}

	public List<PatientModel> getResult() {
		return result;
	}

	public long getPatient_id() {
		return patient_id;
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
	
	public Date getRescheduleDate() {
		return rescheduleDate;
	}

	public List<PatientModel> getPatientsForToday() {
		result = appointmentsForToday().stream()
				.map(p -> new PatientModel(p.getPatient_id(), p.getFirstname() + " " + p.getLastname(), p.getGender(),
						p.getAge(), p.getEmail(), p.getLastAppointment()))
				.collect(Collectors.toList());
		return result;
	}

	private List<PatientEntity> appointmentsForToday() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date today = new Date();
		Date eod = Date.from(today.toInstant().plus(1, ChronoUnit.DAYS));

		return patientDetailService.patientsAppointmentForDay(today, eod);
	}
	
	public void rescheduleAppointment(Long patient_id) {
		PatientEntity toBeUpdatedPatient = patientDetailService.getPatientDetails(patient_id);
		toBeUpdatedPatient.setUpcomingAppointment(rescheduleDate);

		addAppointmentEvent.fire(new AddAppointmentEvent(toBeUpdatedPatient));
	}

}
