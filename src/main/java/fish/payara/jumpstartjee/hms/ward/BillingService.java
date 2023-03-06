package fish.payara.jumpstartjee.hms.ward;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import fish.payara.jumpstartjee.hms.utils.LoggedAndTimed;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BillingService {
	
	private static final Map<WardType, Double> wardCostsPerDay = Map.ofEntries(
		    Map.entry(WardType.GENERAL, 10.00),
		    Map.entry(WardType.PRIVATE, 15.00),
		    Map.entry(WardType.PRIVATE_PLUS, 20.00)
		);
	
	@LoggedAndTimed
	public double calculateBillForWard(WardType wardType, Date numberOfDays) {
		Date today = new Date();
		var diff = today.getTime() - numberOfDays.getTime();
		var daysBetween = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		
		return wardCostsPerDay.get(wardType) * daysBetween;
	}

}
