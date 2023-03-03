package fish.payara.jumpstartjee;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "ItemNotificationEntity")
public class ItemNotificationEntity implements Serializable {

	private static final long serialVersionUID = -6191621345402610905L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "NOTIFY_ID")
	private long notifyId;

	@Column(name = "ITEM_ID")
	private long itemId;

	@Column(name = "EMAIL_ID")
	@Email
	private String emailId;

	@Column(name = "IS_NOTIFIED")
	private boolean isNotified;
	
	public ItemNotificationEntity() {}

	public ItemNotificationEntity(long itemId, String emailId, boolean isNotified) {
		super();
		this.itemId = itemId;
		this.emailId = emailId;
		this.isNotified = isNotified;
	}

	public long getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(long notifyId) {
		this.notifyId = notifyId;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public boolean isNotified() {
		return isNotified;
	}

	public void setNotified(boolean isNotified) {
		this.isNotified = isNotified;
	}

	@Override
	public String toString() {
		return "ItemNotificationEntity [notifyId=" + notifyId + ", itemId=" + itemId + ", emailId=" + emailId
				+ ", isNotified=" + isNotified + "]";
	}
	
}
