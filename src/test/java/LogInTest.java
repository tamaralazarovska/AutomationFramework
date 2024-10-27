
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import com.aventstack.extentreports.Status;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import testComponents.BaseTest;
import testComponents.Retry;

import static testComponents.Listeners.extentTest;

@Listeners(testComponents.Listeners.class)
public class LogInTest extends BaseTest {
    WebDriverWait wait;

    @Test(priority = 2, dataProvider = "getData", retryAnalyzer = Retry.class)
    @Description("Verify that the account is successfully login")
    public void logInTest(HashMap<String, String> input) throws IOException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement allowCookies = wait.until(ExpectedConditions.elementToBeClickable(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")));
        allowCookies.click();
        extentTest.get().log(Status.INFO, "Login account");
        logInPage.login(input.get("email"), input.get("password"));
        Assert.assertEquals("Account", logInPage.getLoginMessage());
        if ("Account".equalsIgnoreCase(logInPage.getLoginMessage()))
            extentTest.get().log(Status.PASS, "Account login successfully!");
        else extentTest.get().log(Status.FAIL, "Account is not logged in");
        restartBrowser();
    }
    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> logInDataList = loadLogInData(System.getProperty("user.dir") + "\\src\\test\\java\\data\\LogIn.json");
        return new Object[][]{{logInDataList.get(0)}, {logInDataList.get(1)}};
    }
}




