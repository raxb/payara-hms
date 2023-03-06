package fish.payara.jumpstartjee;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TemporalType;
import jakarta.transaction.Transactional;

@Transactional
@ApplicationScoped
public class PatientDetailService {

	@PersistenceContext
	private EntityManager em;

	@LoggedAndTimed
	public PatientEntity getPatientDetails(Long patient_id) {
		return em.find(PatientEntity.class, patient_id);
	}

	@LoggedAndTimed
	public PatientEntity savePatientDetails(PatientEntity patientEntity) {
		em.persist(patientEntity);
		return patientEntity;
	}

	@LoggedAndTimed
	public List<PatientEntity> allPatientsByNameAndId() {
		return em
				.createQuery("select p.patient_id, p.firstname, p.lastname, p.upcomingAppointment from PatientEntity p",
						PatientEntity.class)
				.getResultList();
	}

	@LoggedAndTimed
	public List<PatientEntity> patientsAppointmentForDay(Date appointmentDate, Date eod) {
		return em
				.createQuery(
						"select p from PatientEntity p where p.upcomingAppointment between :appointmentDate and :eod",
						PatientEntity.class)
				.setParameter("appointmentDate", appointmentDate, TemporalType.TIMESTAMP)
				.setParameter("eod", eod, TemporalType.TIMESTAMP).getResultList();
	}

	public void updateAppointment(@Observes AddAppointmentEvent addAppointmentEvent) {
		em.merge(addAppointmentEvent.getPatientEntity());
	}

	@LoggedAndTimed
	public Optional<PatientEntity> patientExistsWithEmail(String emailId) {
		return em.createQuery("select patient from PatientEntity patient where patient.email = :emailId",
				PatientEntity.class).setParameter("emailId", emailId).getResultList().stream().findFirst();
	}

	public boolean getPatientWithNameAndEmail(String patientName, String patientEmail) {
		var isPresent = false;
		try {
			var patientToBookWard = em.createQuery(
					"select patient from PatientEntity patient where patient.firstname = :patientName and patient.email = :patientEmail",
					PatientEntity.class).setParameter("patientName", patientName)
					.setParameter("patientEmail", patientEmail).getSingleResult();
			if (patientToBookWard.getPatient_id() != null)
				isPresent = true;
		} catch (Exception e) {
			isPresent = false;
		}
		return isPresent;
	}

}
