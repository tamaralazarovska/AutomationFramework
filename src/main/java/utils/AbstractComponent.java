package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AbstractComponent {
    private WebDriver driver;

    public AbstractComponent(WebDriver driver) {
        this.driver = driver;
    }
    //Decline cookies

    private By declineCookiesBtn = (By.id("CybotCookiebotDialogBodyButtonDecline"));

    public void declineCookies() {
//        driver.findElement(declineCookiesBtn).click();
        WebElement button = driver.findElement(declineCookiesBtn);
        waitForElementVisible(button);

        // Click the button after it is visible
        button.click();
    }

    public void waitForElementVisible(WebElement findBy) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(findBy));

    }
}
