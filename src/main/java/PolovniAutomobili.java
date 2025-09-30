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

        // finding car brand
        driverFirefox.findElement(By.xpath("//p[contains(@title,'Sve marke')]")).click();

    }
}