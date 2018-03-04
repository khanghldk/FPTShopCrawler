/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import crawler.Crawler;
import java.io.File;

/**
 *
 * @author haleduykhang
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            setDriverProperty();
            Crawler crawler = new Crawler();

            crawler.setMainURL("https://fptshop.com.vn/");
            crawler.crawlProducts();
            
            crawler.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public static void setDriverProperty() {
        File file = new File("chromedriver");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
    }

}
