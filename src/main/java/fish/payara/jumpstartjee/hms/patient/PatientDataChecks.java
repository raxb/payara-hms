package fish.payara.jumpstartjee.hms.patient;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PatientDataChecks implements ConstraintValidator<ValidPatient, PatientEntity> {

	private static final Pattern EMAIL_PATTERN = Pattern
			.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

	@Override
	public void initialize(ValidPatient constraintAnnotation) {
		//
	}

	@Override
	public boolean isValid(PatientEntity patient, ConstraintValidatorContext context) {
		return patient.getAge() > 0 && patient.getAge() < 150 && EMAIL_PATTERN.matcher(patient.getEmail()).matches();
	}
}
