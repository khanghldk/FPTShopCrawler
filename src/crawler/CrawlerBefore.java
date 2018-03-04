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

/**
 *
 * @author haleduykhang
 */
public class CrawlerBefore {

    private WebDriver driver;
    private String mainURL;

    public CrawlerBefore() {
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
            switch (key) {
                case "ĐIỆN THOẠI":
                    crawlPhones(entry.getValue());
                    break;
                case "LAPTOP":
                    crawlLaptops(entry.getValue());
                    break;
                case "TABLET":
                    crawlTablets(entry.getValue());
                    break;
                default:
                    crawlAccessories(entry.getValue());
                    break;
            }

        }
    }

    public void crawlPhones(String url) {
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
                List<WebElement> items = driver.findElements(By.xpath("//div[@class='fs-lpil']"));

                for (WebElement e : items) {
                    Phone phone = new Phone();
                    
                    //get Phone name
                    WebElement lpilName = e.findElement(By.xpath("./div/div[@class='fs-lpilname']"));
                    String phoneName = lpilName.findElement(By.xpath("./h3/a")).getText().trim();
                    int spacePosition = phoneName.indexOf(" ");
                    phone.setBrand(phoneName.substring(0, spacePosition));
                    phone.setName(phoneName);
                    try {
                        // get Phone price
                        String phonePrice = lpilName.findElement(By.xpath("./div[@class='fs-lpil-price']/p"))
                                .getText().trim();
                        if (!phonePrice.isEmpty()) {
                            phonePrice = phonePrice.replaceAll("\\.", "");
                            String[] splits = phonePrice.split(" ");
                            phone.setPrice(Integer.parseInt(splits[0]));
                            phone.setCurrency(splits[1]);
                        }
                    } catch (Exception ex) {
                    }

                    // get Others Info of Phone
                    List<WebElement> lpilTskts = e.findElements(By.xpath("./div/div[@class='fs-lpil-tskt']/ul/li"));
                    for (WebElement ele : lpilTskts) {
                        String content = ele.getText().toLowerCase().trim();
                        String[] parts = content.split(":");
                        for (int i = 0; i < parts.length; i++) {
                            parts[i] = parts[i].trim();
                        }
                        if (parts.length > 1) {
                            switch (parts[0]) {
                                case "màn hình":
                                    phone.setScreen(parts[1]);
                                    break;
                                case "pin":
                                    phone.setCell(parts[1]);
                                    break;
                                case "cpu":
                                    phone.setCpu(parts[1]);
                                    break;
                                case "camera":
                                    phone.setCamera(parts[1]);
                                    break;
                                case "ram":
                                    phone.setRam(parts[1]);
                                    break;
                                case "hđh":
                                    phone.setOs(parts[1]);
                                    break;
                            }
                        }
                    }
                    PhoneFunction.add(phone);
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
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crawlLaptops(String url) {
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
                List<WebElement> items = driver.findElements(By.xpath("//div[@class='fs-lapitem']"));

                for (WebElement e : items) {
                    Laptop laptop = new Laptop();
                    WebElement lpilName = e.findElement(By.xpath("./div/div[@class='fs-ilap-itop']"));
                    String laptopName = lpilName.findElement(By.xpath("./h3/a")).getText().trim();
                    int spacePosition = laptopName.indexOf(" ");
                    laptop.setBrand(laptopName.substring(0, spacePosition));
                    laptop.setName(laptopName);
                    String laptopPrice = lpilName.findElement(By.xpath("./div[@class='fs-ilap-price']/p"))
                            .getText().trim();
                    if (!laptopPrice.isEmpty()) {
                        laptopPrice = laptopPrice.replaceAll("\\.", "");
                        String[] splits = new String[2];
                        splits[1] = "";
                        while (!Character.isDigit((laptopPrice.charAt(laptopPrice.length() - 1)))) {
                            splits[1] = laptopPrice.charAt(laptopPrice.length() - 1) + splits[1];
                            laptopPrice = laptopPrice.substring(0, laptopPrice.length() - 1);
                        }
                        splits[0] = laptopPrice;
                        laptop.setPrice(Integer.parseInt(splits[0]));
                        laptop.setCurrency(splits[1]);
                    }

                    List<WebElement> lpilTskts = e.findElements(By.xpath("./div/div[@class='fs-ilap-ch']/ul/li"));
                    for (WebElement ele : lpilTskts) {
                        String content = ele.getText().toLowerCase().trim();
                        String[] parts = content.split(":");
                        for (int i = 0; i < parts.length; i++) {
                            parts[i] = parts[i].trim();
                        }
                        if (parts.length > 1) {
                            switch (parts[0]) {
                                case "màn hình":
                                    laptop.setScreen(parts[1]);
                                    break;
                                case "vga":
                                    laptop.setVga(parts[1]);
                                    break;
                                case "cpu":
                                    laptop.setCpu(parts[1]);
                                    break;
                                case "nặng":
                                    try {
                                        while (!Character.isDigit((parts[1].charAt(parts[1].length() - 1)))) {
                                            parts[1] = parts[1].substring(0, parts[1].length() - 1);
                                        }
                                        parts[1] = parts[1].replaceAll(",", ".");
                                        laptop.setWeight(Double.parseDouble(parts[1]));
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    break;

                                case "ram":
                                    laptop.setRam(parts[1]);
                                    break;
                                case "hđh":
                                    laptop.setOs(parts[1]);
                                    break;
                            }
                        }
                    }
                    LaptopFunction.add(laptop);
                }

                currentTotal += items.size();

                // get current Page
                List<WebElement> pages = driver.findElements(By.xpath("//div[@class='f-cmtpaging']/ul/li"));
                int current = 0;
                while (!pages.get(current).getAttribute("class").equals("active")) {
                    current++;
                }
                current++;
                try {
                    WebElement button = pages.get(current).findElement(By.cssSelector("a"));
                    button.click();
                    while (!waitForJStoLoad());
                } catch (Exception e) {
                }
                System.out.println(currentTotal);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crawlTablets(String url) {
        try {

            this.setMainURL(url);

            int dataTotal = Integer.parseInt(
                    driver.findElement(By.xpath("//div[@class='f-cmtpaging']/a"))
                            .getAttribute("data-total").trim());
            int currentTotal = 0;
            while (currentTotal < dataTotal) {
                List<WebElement> items = driver.findElements(By.xpath("//div[@class='fs-lpil']"));

                for (WebElement e : items) {
                    Tablet tablet = new Tablet();
                    WebElement lpilName = e.findElement(By.xpath("./div/div[@class='fs-lpilname']"));
                    String tabletName = lpilName.findElement(By.xpath("./h3/a")).getText().trim();
                    int spacePosition = tabletName.indexOf(" ");
                    tablet.setBrand(tabletName.substring(0, spacePosition));
                    tablet.setName(tabletName);
                    try {
                        String tabletPrice = lpilName.findElement(By.xpath("./div[@class='fs-lpil-price']/p"))
                                .getText().trim();
                        if (!tabletPrice.isEmpty()) {
                            tabletPrice = tabletPrice.replaceAll("\\.", "");
                            String[] splits = new String[2];
                            splits[1] = "";
                            while (!Character.isDigit((tabletPrice.charAt(tabletPrice.length() - 1)))) {
                                splits[1] = tabletPrice.charAt(tabletPrice.length() - 1) + splits[1];
                                tabletPrice = tabletPrice.substring(0, tabletPrice.length() - 1);
                            }
                            splits[0] = tabletPrice;
                            tablet.setPrice(Integer.parseInt(splits[0]));
                            tablet.setCurrency(splits[1]);
                        }
                    } catch (Exception ex) {
                        //    ex.printStackTrace();
                    }

                    List<WebElement> lpilTskts = e.findElements(By.xpath("./div/div[@class='fs-lpil-tskt']/ul/li"));
                    for (WebElement ele : lpilTskts) {
                        String content = ele.getText().toLowerCase().trim();
                        String[] parts = content.split(":");
                        for (int i = 0; i < parts.length; i++) {
                            parts[i] = parts[i].trim();
                        }
                        if (parts.length > 1) {
                            switch (parts[0]) {
                                case "màn hình":
                                    tablet.setScreen(parts[1]);
                                    break;
                                case "camera":
                                    tablet.setCamera(parts[1]);
                                    break;
                                case "pin":
                                    tablet.setCell(parts[1]);
                                    break;
                                case "ram":
                                    tablet.setRam(parts[1]);
                                    break;
                                case "hđh":
                                    tablet.setOs(parts[1]);
                                    break;
                            }
                        }
                    }
                    TabletFunction.add(tablet);
                }

                currentTotal += items.size();

                // get current Page
                List<WebElement> pages = driver.findElements(By.xpath("//div[@class='f-cmtpaging']/ul/li"));
                int current = 0;
                while (!pages.get(current).getAttribute("class").equals("active")) {
                    current++;
                }
                current++;
                try {
                    WebElement button = pages.get(current).findElement(By.cssSelector("a"));
                    button.click();
                    while (!waitForJStoLoad());
                } catch (Exception e) {
                }
                System.out.println(currentTotal);
            }

        } catch (Exception e) {
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
