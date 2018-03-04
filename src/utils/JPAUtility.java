/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author haleduykhang
 */
public class JPAUtility {
    public static EntityManagerFactory factory;
    
    static {
        factory = Persistence.createEntityManagerFactory("FPTShopCrawlerPU");
    }
    
    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
    
    public static void close() {
        factory.close();
    }
    
}
