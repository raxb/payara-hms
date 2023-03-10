package fish.payara.jumpstartjee.hms.ward;

import fish.payara.jumpstartjee.hms.patient.PatientDetailService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@RequestScoped
public class WardBookingDataChecks implements ConstraintValidator<ValidWardBooking, WardEntity> {
	
	@Inject
	private PatientDetailService patientDetailService;

	@Override
	public void initialize(ValidWardBooking constraintAnnotation) {
		//
	}

	@Override
	public boolean isValid(WardEntity wardEntity, ConstraintValidatorContext context) {
		return patientDetailService.getPatientWithNameAndEmail(wardEntity.getPatientName(), wardEntity.getPatientEmail());
	}
}