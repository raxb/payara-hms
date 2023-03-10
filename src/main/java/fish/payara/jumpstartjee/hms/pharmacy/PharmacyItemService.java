package fish.payara.jumpstartjee.hms.pharmacy;

import java.util.List;

import fish.payara.jumpstartjee.hms.utils.LoggedAndTimed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
@ApplicationScoped
@LoggedAndTimed
public class PharmacyItemService {

	@PersistenceContext
	private EntityManager em;

	public List<PharmacyEntity> searchForItem(String itemName) {
		return em.createQuery("select item from PharmacyEntity item where item.itemName like :itemName",
				PharmacyEntity.class).setParameter("itemName", "%" + itemName + "%").getResultList();
	}

	public List<PharmacyEntity> itemInventory() {
		return em.createQuery("select item from PharmacyEntity item", PharmacyEntity.class).getResultList();
	}

	public PharmacyEntity addItemToInventory(PharmacyEntity item, Long quantity) {
		var pharmacyEntity = em.find(PharmacyEntity.class, item.getItemId());
		if(pharmacyEntity != null) {
			pharmacyEntity.setItemQuantity(pharmacyEntity.getItemQuantity()+quantity);
			return em.merge(pharmacyEntity);
		} else {
			em.persist(item);
			em.flush();
			return em.find(PharmacyEntity.class, item.getItemId());
		}
	}

}
