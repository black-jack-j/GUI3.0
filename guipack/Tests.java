package guipack;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import gui.Keeper;
import gui.Territory;

public class Tests {
    public static void main(String[] args){
    	final EntityManagerFactory emf = Persistence.createEntityManagerFactory("maps");
        EntityManager em = emf.createEntityManager();
    	Scanner scr = new Scanner(System.in);
    	em.getTransaction().begin();
		List<Keeper> lk = em.createNamedQuery("FindAll").getResultList();
		lk.forEach((Keeper kp)->kp.printStorage());
		em.getTransaction().commit();
    	while (true){
    		if (scr.hasNextLine()){
    			String s = scr.nextLine();
    			String[] prm = s.split(" ");
    			Keeper k = new Keeper(prm[0]);
    			for(int i = 1; i<prm.length; i = i+2){
    				Territory tmp = new Territory(prm[i], Double.parseDouble(prm[i+1]));
    				tmp.setMap(k);
    				k.addComponent(tmp);
    				k.printStorage();
    			}
    			em.getTransaction().begin();
    			em.persist(k);
    			em.getTransaction().commit();
    		}
    	}
    }
}
