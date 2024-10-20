import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageObjects.WishlistPage;
import testComponents.BaseTest;
import utils.AbstractComponent;

import java.time.Duration;

@Listeners(testComponents.Listeners.class)
public class WishlistTest extends BaseTest {

    WishlistPage wishlistPage;
    WebDriverWait wait;
    AbstractComponent abstractComponent;
    @BeforeMethod
    public void setup() {
        wishlistPage = new WishlistPage(driver);
    }

    @Test(priority=1,testName="Positive scenario for adding a product to wishlist")
    public void addToWishlistTest(){
        wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        WebElement declineCookies = wait.until(ExpectedConditions.elementToBeClickable(By.id("CybotCookiebotDialogBodyButtonDecline")));
        declineCookies.click();
        wishlistPage.searchProduct();
        wishlistPage.addToWishlist();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast__message")));
        String actualResult = toastMessage.getText();

        System.out.println(actualResult);
        String expectedResult ="The product has been added to your wishlist.";
        Assert.assertEquals(actualResult,expectedResult);

    }
}
