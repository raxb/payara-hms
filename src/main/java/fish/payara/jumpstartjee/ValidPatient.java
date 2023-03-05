package fish.payara.jumpstartjee;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(value={ElementType.TYPE_USE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy={PatientDataChecks.class})
public @interface ValidPatient {
	
	String message() default "Patient Details are insufficient - Constraints not met!";//Add resource builder for I18N

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
