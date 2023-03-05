package fish.payara.jumpstartjee;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BillingService {
	
	private static final Map<WardType, Double> wardCostsPerDay = Map.ofEntries(
		    Map.entry(WardType.GENERAL, 10.00),
		    Map.entry(WardType.PRIVATE, 15.00),
		    Map.entry(WardType.PRIVATE_PLUS, 20.00)
		);
	
	public double calculateBillForWard(WardType wardType, Date numberOfDays) {
		Date today = new Date();
		var diff = today.getTime() - numberOfDays.getTime();
		System.out.println("--------------diff-------"+diff);
		var daysBetween = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		System.out.println("--------------daysBetween-------"+daysBetween);
		
		return wardCostsPerDay.get(wardType) * daysBetween;
	}

}
