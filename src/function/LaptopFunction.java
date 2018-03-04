/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

import data.Laptop;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import utils.JPAUtility;

/**
 *
 * @author haleduykhang
 */
public class LaptopFunction {
    public static void add(Laptop laptop) {
        EntityManager em = JPAUtility.getEntityManager();
        try {
            em.getTransaction().begin();
            List<Laptop> list = findByName(laptop.getName());
            if (list.isEmpty()) {
                em.persist(laptop);
                em.flush();
            } else {
                laptop.setId(list.get(0).getId());
                em.merge(laptop);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public static List<Laptop> findByName(String name) {
        EntityManager em = JPAUtility.getEntityManager();
        List<Laptop> result = null;
        try {
            Query query = em.createNamedQuery("Laptop.findByName");
            query.setParameter("name", name);
            result = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }
}
