package fish.payara.jumpstartjee.hms.pharmacy;

import java.util.List;
import java.util.stream.Collectors;

import fish.payara.jumpstartjee.hms.notification.NotificationService;
import fish.payara.jumpstartjee.hms.patient.PatientDetailService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class PharmacyModel {

	@Inject
	private PharmacyItemService pharmacyItemService;
	
	@Inject
	private NotificationService notificationService;
	
	@Inject 
	private PatientDetailService patientDetailService;

	private Long itemId;
	private String itemName;
	private Long itemQuantity;
	private Double itemPrice;
	private List<PharmacyModel> pharmacyModelData;
	private String notifyForEmail;
	
	@PostConstruct
	public void init() {
		pharmacyModelData = pharmacyItemService.itemInventory().stream().map(inventory -> new PharmacyModel(inventory.getItemId(), inventory.getItemName(),
				inventory.getItemQuantity(), inventory.getItemPrice())).collect(Collectors.toList());
	}

	public PharmacyModel() {
	}

	public PharmacyModel(Long itemId, String itemName, Long itemQuantity, Double itemPrice) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemQuantity = itemQuantity;
		this.itemPrice = itemPrice;
	}

	public Long getItemId() {
		return itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public Long getItemQuantity() {
		return itemQuantity;
	}

	public Double getItemPrice() {
		return itemPrice;
	}
	
	public List<PharmacyModel> getPharmacyModelData() {
		return pharmacyModelData;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getNotifyForEmail() {
		return notifyForEmail;
	}

	public void setNotifyForEmail(String notifyForEmail) {
		this.notifyForEmail = notifyForEmail;
	}

	public void searchForItem() {
		pharmacyModelData = pharmacyItemService.searchForItem(itemName)
				.stream().map(pharmacyEntity -> new PharmacyModel(pharmacyEntity.getItemId(), pharmacyEntity.getItemName(),
						pharmacyEntity.getItemQuantity(), pharmacyEntity.getItemPrice())).collect(Collectors.toList());

	}
	
	public void addToNotification(Long itemId) throws Exception {
		
		if(!patientDetailService.patientExistsWithEmail(notifyForEmail).isPresent()) {
			throw new Exception("\n There is no patient registered with the emailId " + notifyForEmail +"\n Please register the patient details!");
		}
		
		notificationService.addToNotify(itemId, notifyForEmail);
	}

}
