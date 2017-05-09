package conservation;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import gui.Keeper;

public class DBSaver implements Watchdog{
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("maps");
    private static EntityManager em = emf.createEntityManager();
    private BlockingQueue<String> que;
	public DBSaver(){
		que = new ArrayBlockingQueue<String>(1);
	}
	
	public void save(Keeper keeper) {
		try {
			que.put("new");
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (keeper == null) return;
		Keeper k = (Keeper)em.find(Keeper.class, keeper.getId());
		em.getTransaction().begin();
		if (k == null){
			em.persist(keeper);
		}else em.merge(keeper);
		em.getTransaction().commit();
		que.clear();
	}

	
	public Keeper load() {
		try {
			que.put("new");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			Keeper k = (Keeper) em.createNamedQuery("FindAll").getSingleResult();
			que.clear();
			return k;
		}catch(NoResultException e){
			Keeper k = new Keeper("Default");
			em.getTransaction().begin();
			em.persist(k);
			em.getTransaction().commit();
			return k;
		}
	}
}
