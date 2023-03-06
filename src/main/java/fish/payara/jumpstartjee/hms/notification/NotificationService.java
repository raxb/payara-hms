package fish.payara.jumpstartjee.hms.notification;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import fish.payara.jumpstartjee.hms.utils.AddItemEvent;
import fish.payara.jumpstartjee.hms.utils.LoggedAndTimed;
import jakarta.ejb.Schedule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
@ApplicationScoped
@LoggedAndTimed
public class NotificationService {
	
	@PersistenceContext
	private EntityManager em;
	
	public void updateNotificationOnItemAvailability(@Observes AddItemEvent addItemEvent) {
		var itemForNotification = addItemEvent.getPharmacyEntity();
		var updateIsNotifiedForItem = em.createQuery("select n from ItemNotificationEntity n where n.itemId = :itemId", ItemNotificationEntity.class)
		.setParameter("itemId", itemForNotification.getItemId()).getResultList();
		
		var flipToNotify = updateIsNotifiedForItem.stream().filter(Predicate.not((ItemNotificationEntity::isNotified))).collect(Collectors.toList());
		for(ItemNotificationEntity notificationEntity : flipToNotify) {
			notificationEntity.setNotified(true);
			em.merge(notificationEntity);
		}
	}

	public void addToNotify(Long itemId, String notifyForEmail) {
		ItemNotificationEntity itemNotificationEntity = new ItemNotificationEntity(itemId, notifyForEmail, false);
		em.persist(itemNotificationEntity);
		System.out.println(itemNotificationEntity.toString());
	}
}
