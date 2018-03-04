/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

import data.Tablet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import utils.JPAUtility;

/**
 *
 * @author haleduykhang
 */
public class TabletFunction {
    public static void add(Tablet tablet) {
        EntityManager em = JPAUtility.getEntityManager();
        try {
            em.getTransaction().begin();
            List<Tablet> list = findByName(tablet.getName());
            if (list.isEmpty()) {
                em.persist(tablet);
                em.flush();
            } else {
                tablet.setId(list.get(0).getId());
                em.merge(tablet);
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
    
    public static List<Tablet> findByName(String name) {
        EntityManager em = JPAUtility.getEntityManager();
        List<Tablet> result = null;
        try {
            Query query = em.createNamedQuery("Tablet.findByName");
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
