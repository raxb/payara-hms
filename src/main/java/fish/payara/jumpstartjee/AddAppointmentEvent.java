package fish.payara.jumpstartjee;

import fish.payara.jumpstartjee.hms.patient.PatientEntity;

public class AddAppointmentEvent {

	private PatientEntity patientEntity;

	public AddAppointmentEvent(PatientEntity patientEntity) {
		super();
		this.patientEntity = patientEntity;
	}

	public PatientEntity getPatientEntity() {
		return patientEntity;
	}

}
