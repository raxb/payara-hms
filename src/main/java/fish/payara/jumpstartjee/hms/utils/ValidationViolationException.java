package fish.payara.jumpstartjee.hms.utils;

import java.util.List;

public class ValidationViolationException extends HMSException {

	private static final long serialVersionUID = -8980818157903556291L;
	private final List<String> violations;
	
	public ValidationViolationException(List<String> collect) {
		this.violations = collect;
	}

	public List<String> getMessages() {
		return violations;
	}
	
}
