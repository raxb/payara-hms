package fish.payara.jumpstartjee;

public class AddItemEvent {

	private PharmacyEntity pharmacyEntity;

	public AddItemEvent(PharmacyEntity pharmacyEntity) {
		this.pharmacyEntity = pharmacyEntity;
	}

	public PharmacyEntity getPharmacyEntity() {
		return pharmacyEntity;
	}

}
