package fish.payara.jumpstartjee;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
@ApplicationScoped
@LoggedAndTimed
public class WardService {

	@PersistenceContext
	private EntityManager em;

	public void bookWard(WardEntity wardEntity) {
		em.persist(wardEntity);
	}

	public List<WardEntity> getAllWardDetails() {
		return em.createQuery("select ward from WardEntity ward",WardEntity.class).getResultList();
	}

}
