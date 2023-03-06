package fish.payara.jumpstartjee.hms.pharmacy;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "PharmacyEntity")
public class PharmacyEntity implements Serializable {

	private static final long serialVersionUID = -7917085157291961877L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ITEM_ID")
	private Long itemId;

	@Column(name = "ITEM_NAME")
	private String itemName;
	
	@Column(name = "ITEM_QUANTITY")
	private Long itemQuantity;
	
	@PositiveOrZero
	@Column(name = "ITEM_PRICE")
	private Double itemPrice;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Long itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

}
