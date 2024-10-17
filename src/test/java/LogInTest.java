
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import testComponents.BaseTest;

public class LogInTest extends BaseTest {

    @Test(priority = 1, dataProvider = "getData")
    public void logInTest(HashMap<String, String> input) throws IOException {
        logInPage.login(input.get("email"), input.get("password"));
        Assert.assertEquals("Account", logInPage.getLoginMessage());
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> logInDataList = loadLogInData(System.getProperty("user.dir") + "\\src\\test\\java\\data\\LogIn.json");
        return new Object[][]{{logInDataList.get(0)}, {logInDataList.get(1)}};
    }
}




