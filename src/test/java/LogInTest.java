
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import testComponents.BaseTest;
import utils.AbstractComponent;


@Listeners(testComponents.Listeners.class)
public class LogInTest extends BaseTest {
    WebDriverWait wait;

    @Test(priority = 1, dataProvider = "getData")
    public void logInTest(HashMap<String, String> input) throws IOException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        WebElement declineCookies = wait.until(ExpectedConditions.elementToBeClickable(By.id("CybotCookiebotDialogBodyButtonDecline")));
        declineCookies.click();
        logInPage.login(input.get("email"), input.get("password"));
        Assert.assertEquals("Account", logInPage.getLoginMessage());
    }
    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> logInDataList = loadLogInData(System.getProperty("user.dir") + "\\src\\test\\java\\data\\LogIn.json");
        return new Object[][]{{logInDataList.get(0)}, {logInDataList.get(1)}};
    }
}




