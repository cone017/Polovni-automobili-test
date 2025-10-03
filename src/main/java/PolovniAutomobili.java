import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;

public class PolovniAutomobili {

    public static class Car {

        String title;
        String price;
        int year;
        String link;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public Car(String title, String price, int year, String link) {
            this.title = title;
            this.price = price;
            this.year = year;
            this.link = link;
        }
    }

    public static void main(String[] args) {

        FirefoxOptions options = new FirefoxOptions();
        options.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.EAGER);

        System.setProperty("webdriver.gecko.driver", "/home/cone017/Documents/Java aplikacije/Firefox driver/geckodriver");
        WebDriver driverFirefox = new FirefoxDriver(options);
        WebDriverWait wait = new WebDriverWait(driverFirefox, Duration.ofSeconds(5));

        driverFirefox.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // opening window and cookies problem
        driverFirefox.get("https://www.polovniautomobili.com/");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class, 'js-accept-cookies')]"))).click();
        driverFirefox.manage().window().maximize();

        // finding car brand - Fiat (Punto, Grande Punto)
        driverFirefox.findElement(By.xpath("//span[normalize-space()='Sve marke']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[normalize-space()='Fiat']"))).click();
        driverFirefox.findElement(By.xpath("//p[contains(@title, ' Svi modeli')]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[normalize-space()='Grande Punto']"))).click();
        driverFirefox.findElement(By.xpath("//label[normalize-space()='Punto']")).click();

        // filtering range of years of car made - 2005 | 2013
        driverFirefox.findElement(By.xpath("//p[contains(@title, 'Godište od')]")).click();
        driverFirefox.findElement(By.xpath("//div[@class='SumoSelect sumo_year_from open']//label[contains(text(),'2005 god.')]")).click();
        driverFirefox.findElement(By.xpath("//p[contains(@title, ' do') and contains(@class, 'CaptionCont SelectBox')]")).click();
        driverFirefox.findElement(By.xpath("//div[@class='SumoSelect sumo_year_to open']//label[contains(text(), '2013 god.')]")).click();

        // filtering region - Sumadijski
        driverFirefox.findElement(By.xpath("//span[normalize-space()='Region']")).click();
        driverFirefox.findElement(By.xpath("//label[contains(text(),'Šumadijski')]")).click();

        // confirming selection
        driverFirefox.findElement(By.xpath("//button[@name='submit_1']")).click();

        Document doc = null;

        try {
            doc = Jsoup.connect(driverFirefox.getCurrentUrl()).get();
            // use doc here
        } catch (IOException e) {
            e.printStackTrace(); // or custom error handling
        }

        Elements cars = doc.select("article.classified");
        List<Car> carList = new ArrayList<>();

        for (Element article : cars) {
            String title = article.select("h2 a.ga-title").attr("title");

            // price (from data attribute or div.price)
            String price = article.attr("data-price");
            if (price.isEmpty()) {
                price = article.select("div.price").text();
            }

            // year (first .setInfo .top)
            String yearText = article.select(".info .setInfo .top").first().text();
            int year = 0;
            if (yearText.matches("\\d{4}.*")) { // starts with 4 digits
                year = Integer.parseInt(yearText.substring(0, 4));
            }

            // link (relative href → prepend domain)
            String link = article.select("h2 a.ga-title").attr("href");
            if (!link.startsWith("http")) {
                link = "https://www.polovniautomobili.com" + link;
            }

            carList.add(new Car(title, price, year, link));
        }

        try {
            ExcelExporter.exportCarsToExcel(carList, "Fiat - " + LocalDate.now() + ".xlsx");
            System.out.println("✅ cars.xlsx created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        driverFirefox.quit();
    }
}