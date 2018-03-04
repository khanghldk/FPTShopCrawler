/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

import data.Accessory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import utils.JPAUtility;

/**
 *
 * @author haleduykhang
 */
public class AccessoryFunction {
    public static void add(Accessory accessory) {
        EntityManager em = JPAUtility.getEntityManager();
        try {
            em.getTransaction().begin();
            List<Accessory> list = findByName(accessory.getName());
            if (list.isEmpty()) {
                em.persist(accessory);
                em.flush();
            } else {
                accessory.setId(list.get(0).getId());
                em.merge(accessory);
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
    
    public static List<Accessory> findByName(String name) {
        EntityManager em = JPAUtility.getEntityManager();
        List<Accessory> result = null;
        try {
            Query query = em.createNamedQuery("Accessory.findByName");
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
