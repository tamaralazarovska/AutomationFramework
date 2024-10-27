import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
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

import static testComponents.Listeners.extentTest;

@Listeners(testComponents.Listeners.class)
public class WishlistTest extends BaseTest {

    WishlistPage wishlistPage;
    WebDriverWait wait;
    AbstractComponent abstractComponent;
    @BeforeMethod
    public void setup() {
        wishlistPage = new WishlistPage(driver);
    }

@Test(priority = 3, testName = "Positive scenario for adding a product to wishlist")
public void addToWishlistTest() {
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    try {
        // Wait for and decline cookie consent
        WebElement allowCookies = wait.until(ExpectedConditions.elementToBeClickable(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")));
        allowCookies.click();
        extentTest.get().log(Status.INFO, "Declined cookie consent");

        // Adding product to wishlist
        extentTest.get().log(Status.INFO, "Adding product to wishlist");
        wishlistPage.searchProduct();
        wishlistPage.addToWishlist();

        // Wait for the toast message to become visible
        WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast__message")));
        String actualResult = toastMessage.getText();

        System.out.println(actualResult);
        String expectedResult = "The product has been added to your wishlist.";
        Assert.assertEquals(actualResult, expectedResult);

        if (expectedResult.equalsIgnoreCase(actualResult)) {
            extentTest.get().log(Status.PASS, "Product has been added to wishlist successfully!");
        } else {
            extentTest.get().log(Status.FAIL, "Product is not added to wishlist");
        }
    } catch (ElementNotInteractableException e) {
        extentTest.get().log(Status.FAIL, "Element not interactable: " + e.getMessage());
    } catch (Exception e) {
        extentTest.get().log(Status.FAIL, "An error occurred: " + e.getMessage());
    } finally {
        restartBrowser();
    }
    }
}
