package fish.payara.jumpstartjee.hms.notification;

import fish.payara.jumpstartjee.LoggedAndTimed;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Singleton
public class NotificationScheduler {
	
	@PersistenceContext
	private EntityManager em;
	
	@LoggedAndTimed
	@Schedule(second = "*/5", minute = "*/30", hour = "*", persistent=false)
    public void automaticallyScheduled() {
		System.out.println("===============> Notification Scheduler <===============");
		var sendNotification = em.createQuery("select n from ItemNotificationEntity n where n.isNotified = true", ItemNotificationEntity.class).getResultList();
		sendNotification.stream().map(ItemNotificationEntity::getEmailId).forEach(t -> System.out.println("===============> Sending Mail "+ t +" to Notify ..."));
	}

}
