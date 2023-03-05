package fish.payara.jumpstartjee;

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
		System.out.println("-------------------------------------------"+wardEntity.getPatientName()+"////\\\\"+wardEntity.getPatientEmail());
		return patientDetailService.getPatientWithNameAndEmail(wardEntity.getPatientName(), wardEntity.getPatientEmail());
	}
}