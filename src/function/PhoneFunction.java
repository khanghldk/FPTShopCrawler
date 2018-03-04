/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

import data.Phone;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import utils.JPAUtility;

/**
 *
 * @author haleduykhang
 */
public class PhoneFunction {
    public static void add(Phone phone) {
        EntityManager em = JPAUtility.getEntityManager();
        try {
            em.getTransaction().begin();
            List<Phone> list = findByName(phone.getName());
            if (list.isEmpty()) {
                em.persist(phone);
                em.flush();
            } else {
                phone.setId(list.get(0).getId());
                em.merge(phone);
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
    
    public static List<Phone> findByName(String name) {
        EntityManager em = JPAUtility.getEntityManager();
        List<Phone> result = null;
        try {
            Query query = em.createNamedQuery("Phone.findByName");
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
