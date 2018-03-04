/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import data.Accessory;
import data.Laptop;
import data.Phone;
import data.Tablet;
import function.AccessoryFunction;
import function.LaptopFunction;
import function.PhoneFunction;
import function.TabletFunction;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.seleniumhq.jetty9.util.log.Log;
import org.seleniumhq.jetty9.util.log.Logger;

/**
 *
 * @author haleduykhang
 */
public class Crawler {

    private WebDriver driver;
    private String mainURL;

    public Crawler() {
        this.driver = new ChromeDriver();
    }

    public void setMainURL(String mainURL) {
        this.mainURL = mainURL;
        this.driver.get(mainURL);
    }

    public void close() {
        this.driver.quit();
    }

    public Map<String, String> getMainLinks() {
        Map<String, String> result = new HashMap<>();

        List<WebElement> list = driver.findElements(By.xpath("//ul[@class='fs-mnul clearfix']/li/a"));
        list.forEach((WebElement e) -> {
            String title = e.getText().trim();
            if (title.equals("ĐIỆN THOẠI") || title.equals("LAPTOP")
                    || title.equals("TABLET") || title.equals("PHỤ KIỆN")) {
                result.put(title, e.getAttribute("href"));
            }
        });

        return result;
    }

    public void crawlProducts() {
        Map<String, String> categories = this.getMainLinks();
        for (Map.Entry<String, String> entry : categories.entrySet()) {
            String key = entry.getKey();
            String itemsString;
            String itemNameString;
            String itemPriceString;
            String itemTskt;
            switch (key) {
                case "ĐIỆN THOẠI":
                    itemsString = "//div[@class='fs-lpil']";
                    itemNameString = "./div/div[@class='fs-lpilname']";
                    itemPriceString = "./div[@class='fs-lpil-price']/p";
                    itemTskt = "./div/div[@class='fs-lpil-tskt']/ul/li";
                    crawlType(entry.getValue(), Phone.class, PhoneFunction.class,
                            itemsString, itemNameString, itemPriceString, itemTskt);
                    break;
                case "LAPTOP":
                    itemsString = "//div[@class='fs-lapitem']";
                    itemNameString = "./div/div[@class='fs-ilap-itop']";
                    itemPriceString = "./div[@class='fs-ilap-price']/p";
                    itemTskt = "./div/div[@class='fs-ilap-ch']/ul/li";
                    crawlType(entry.getValue(), Laptop.class, LaptopFunction.class,
                            itemsString, itemNameString, itemPriceString, itemTskt);
                    break;
                case "TABLET":
                    itemsString = "//div[@class='fs-lpil']";
                    itemNameString = "./div/div[@class='fs-lpilname']";
                    itemPriceString = "./div[@class='fs-lpil-price']/p";
                    itemTskt = "./div/div[@class='fs-lpil-tskt']/ul/li";
                    crawlType(entry.getValue(), Tablet.class, TabletFunction.class,
                            itemsString, itemNameString, itemPriceString, itemTskt);
                    break;
                default:
                    crawlAccessories(entry.getValue());
                    break;
            }

        }
    }

    public void crawlType(String url, Class objClass, Class functionClass,
            String itemsString, String itemNameString, String itemPriceString, String itemTskt) {

        try {
            this.setMainURL(url);
            List<WebElement> types = this.driver.findElements(By.xpath("//ul[@class='fs-cteul']/li"));
            String allURL = "";

            for (WebElement e : types) {
                String text = e.findElement(By.xpath("./a/p")).getText().trim().toLowerCase();
                if (text.equals("xem tất cả")) {
                    allURL = e.findElement(By.xpath("./a")).getAttribute("href");
                }
            }
            if (!allURL.equals("")) {
                this.setMainURL(allURL);
            }

            int dataTotal = Integer.parseInt(
                    driver.findElement(By.xpath("//div[@class='f-cmtpaging']/a"))
                            .getAttribute("data-total").trim());

            int currentTotal = 0;

            while (currentTotal < dataTotal) {
                List<WebElement> items = driver.findElements(By.xpath(itemsString));
                for (WebElement e : items) {
                    Class<?> klass = Class.forName(objClass.getName());
                    Object obj = klass.newInstance();
                    //get Product name
                    WebElement lpilName = e.findElement(By.xpath(itemNameString));
                    String name = lpilName.findElement(By.xpath("./h3/a")).getText().trim();
                    int spacePosition = name.indexOf(" ");

                    Method method = klass.getMethod("setBrand", String.class);
                    method.invoke(obj, name.substring(0, spacePosition));
                    method = klass.getMethod("setName", String.class);
                    method.invoke(obj, name);

                    try {
                        // get Product price
                        String price = lpilName.findElement(By.xpath(itemPriceString))
                                .getText().trim();
                        if (!price.isEmpty()) {
                            price = price.replaceAll("\\.", "");
                            String[] splits = new String[2];
                            splits[1] = "";
                            while (!Character.isDigit((price.charAt(price.length() - 1)))) {
                                splits[1] = price.charAt(price.length() - 1) + splits[1];
                                price = price.substring(0, price.length() - 1);
                            }
                            splits[0] = price;

                            method = klass.getMethod("setPrice", Integer.class);
                            method.invoke(obj, Integer.parseInt(splits[0]));

                            method = klass.getMethod("setCurrency", String.class);
                            method.invoke(obj, splits[1]);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    // get Others Info of Product
                    List<WebElement> lpilTskts = e.findElements(By.xpath(itemTskt));
                    for (WebElement ele : lpilTskts) {
                        String[] parts = ele.getText().toLowerCase().trim().split(":");
                        for (int i = 0; i < parts.length; i++) {
                            parts[i] = parts[i].trim();
                        }
                        if (parts.length > 1) {
                            switch (parts[0]) {
                                case "màn hình":
                                    method = klass.getMethod("setScreen", String.class);
                                    method.invoke(obj, parts[1]);
                                    break;
                                case "pin":
                                    method = klass.getMethod("setCell", String.class);
                                    method.invoke(obj, parts[1]);
                                    break;
                                case "cpu":
                                    method = klass.getMethod("setCpu", String.class);
                                    method.invoke(obj, parts[1]);
                                    break;
                                case "camera":
                                    method = klass.getMethod("setCamera", String.class);
                                    method.invoke(obj, parts[1]);
                                    break;
                                case "ram":
                                    method = klass.getMethod("setRam", String.class);
                                    method.invoke(obj, parts[1]);
                                    break;
                                case "hđh":
                                    method = klass.getMethod("setOs", String.class);
                                    method.invoke(obj, parts[1]);
                                    break;
                                case "vga":
                                    method = klass.getMethod("setVga", String.class);
                                    method.invoke(obj, parts[1]);
                                    break;
                                case "nặng":
                                    try {
                                        while (!Character.isDigit((parts[1].charAt(parts[1].length() - 1)))) {
                                            parts[1] = parts[1].substring(0, parts[1].length() - 1);
                                        }
                                        parts[1] = parts[1].replaceAll(",", ".");
                                        method = klass.getMethod("setWeight", Double.class);
                                        method.invoke(obj, Double.parseDouble(parts[1]));
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    Class<?> fClass = Class.forName(functionClass.getName());
                    Object object = fClass.newInstance();
                    Method functionMethod = fClass.getMethod("add", objClass);
                    functionMethod.invoke(object, obj);
                }

                currentTotal += items.size();

                // get current Page
                List<WebElement> pages = driver.findElements(By.xpath("//div[@class='f-cmtpaging']/ul/li"));
                int currentPage = 0;
                while (!pages.get(currentPage).getAttribute("class").equals("active")) {
                    currentPage++;
                }
                currentPage++;
                // click next Page
                try {
                    WebElement button = pages.get(currentPage).findElement(By.cssSelector("a"));
                    button.click();
                    while (!waitForJStoLoad());
                } catch (Exception e) {
                    System.out.println("There has no page left.");
                }

            }
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void crawlAccessories(String url) {
        try {
            this.setMainURL(url);
            // get Categories Link 
            List<WebElement> listCategories = driver.findElements(By.xpath("//ul[@class='nganhhang']/li"));
            List<String> listURL = new ArrayList<>();
            System.out.println(listCategories.size());
            for (int i = 0; i < listCategories.size(); i++) {
                String itemURL = listCategories.get(i).findElement(By.xpath("./a")).getAttribute("href");
                listURL.add(itemURL);
            }

            for (String link : listURL) {
                this.setMainURL(link);
                boolean haveMore = true;
                while (haveMore) {
                    try {
                        WebElement totalE = driver.findElement(By.xpath("//a[@class='loadmore']"));
                        totalE.click();
                        while (!waitForJStoLoad());
                    } catch (Exception e) {
                        haveMore = false;
                    }
                }

                List<WebElement> items = driver.findElements(By.xpath("//div[@class='p2item p2-lprod-ibox']"));

                for (WebElement item : items) {
                    String name = item.findElement(By.xpath("./div/a")).getText();
                    String price = item.findElement(By.xpath("./div/p")).getText();
                    price = price.replaceAll("\\.", "");
                    String currency = "";
                    while (!Character.isDigit(price.charAt(price.length() - 1))) {
                        currency = price.charAt(price.length() - 1) + currency;
                        price = price.substring(0, price.length() - 1);
                    }
                    int priceInt = Integer.parseInt(price);
                    Accessory acc = new Accessory();
                    acc.setName(name);
                    acc.setPrice(priceInt);
                    acc.setCurrency(currency);
                    AccessoryFunction.add(acc);
                }
            }

        } catch (Exception e) {
        }
    }

    public boolean waitForJStoLoad() {

        WebDriverWait wait = new WebDriverWait(driver, 30);

        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };

        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };

        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }

}
