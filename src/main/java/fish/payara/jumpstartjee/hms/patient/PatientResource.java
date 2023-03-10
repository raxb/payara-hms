package fish.payara.jumpstartjee.hms.patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

import fish.payara.jumpstartjee.hms.utils.AddAppointmentEvent;
import fish.payara.jumpstartjee.hms.utils.ValidationViolationException;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/patient")
public class PatientResource {

	@Inject
	private PatientDetailService patientDetailService;

	@Inject
	private Event<AddAppointmentEvent> addAppointmentEvent;

	@Inject
	private ValidatorFactory validatorFactory;

	// Get patient for patient_id
	@GET
	@Path("/{patient_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PatientEntity getPatientDetails(@PathParam("patient_id") @Positive Long patient_id) {
		var details = patientDetailService.getPatientDetails(patient_id);
		return details;
	}

	// Post patient
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public PatientEntity savePatientDetails(@Valid PatientEntity patientEntity) {
		return patientDetailService.savePatientDetails(patientEntity);
	}

	@GET
	@Path("/allPatients")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PatientEntity> allPatients() {
		return patientDetailService.allPatientsByNameAndId();
	}

	@GET
	@Path("/appointments/{appointmentDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PatientEntity> appointments(@PathParam("appointmentDate") String appointmentDate) {
		Date date = parseStringDate(appointmentDate);
		Date eod = Date.from(date.toInstant().plus(1, ChronoUnit.DAYS));

		return patientDetailService.patientsAppointmentForDay(date, eod);
	}

	private Date parseStringDate(String appointmentDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date;
		try {
			date = format.parse(appointmentDate);
		} catch (ParseException e) {
			date = Date.from(Instant.now());
		}
		return date;
	}

	@PATCH
	@Path("/{patient_id}/{appointmentDate}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public PatientEntity bookAppointment(PatientEntity patient, @PathParam("patient_id") Long patient_id,
			@PathParam("appointmentDate") String appointmentDate) throws Exception {
		if (patientDetailService.getPatientDetails(patient_id) == null) {
			throw new Exception(
					"Incorrect patient details, please ensure you sent the correct patient_id in the requests");
		}
		Date date = parseStringDate(appointmentDate);

		PatientEntity toBeUpdatedPatient = patientDetailService.getPatientDetails(patient_id);
		toBeUpdatedPatient.setUpcomingAppointment(date);

		addAppointmentEvent.fire(new AddAppointmentEvent(toBeUpdatedPatient));
		// TODO: send notification
		return patientDetailService.getPatientDetails(patient_id);
	}

	public void validateAndsavePatientDetails(PatientEntity patientEntity) {
		Set<ConstraintViolation<PatientEntity>> violations = validatorFactory.getValidator().validate(patientEntity);
		if (!violations.isEmpty()) {
			var violationMessages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
			throw new ValidationViolationException(violationMessages);
			
		}
	}

}
