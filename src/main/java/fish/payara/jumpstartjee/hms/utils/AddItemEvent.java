package fish.payara.jumpstartjee.hms.utils;

import fish.payara.jumpstartjee.hms.pharmacy.PharmacyEntity;

public class AddItemEvent {

	private PharmacyEntity pharmacyEntity;

	public AddItemEvent(PharmacyEntity pharmacyEntity) {
		this.pharmacyEntity = pharmacyEntity;
	}

	public PharmacyEntity getPharmacyEntity() {
		return pharmacyEntity;
	}

}
