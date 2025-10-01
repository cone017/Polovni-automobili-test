import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PolovniAutomobili {
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
        driverFirefox.findElement(By.xpath("//label[normalize-space()='Fiat']")).click();
        driverFirefox.findElement(By.xpath("//p[contains(@title, 'Svi modeli')]")).click();
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

        //driverFirefox.quit();

    }
}