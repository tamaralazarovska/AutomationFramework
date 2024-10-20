package testComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import resources.ExtentReportersNG;
import com.aventstack.extentreports.ExtentTest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Listeners extends BaseTest implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    ExtentReports extent = ExtentReportersNG.getReportObject();
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); //Thread safe


    @Override
    public void onTestStart(ITestResult result) {
        String className = result.getTestClass().getName(); // Get the class name
        String methodName = result.getMethod().getMethodName(); // Get the method name

        // Create test with both class and method names
        extentTest.set(extent.createTest(className + " - " + methodName));

        // Log the test start
        String message = "Test Started: " + className + " - " + methodName;
        extentTest.get().info(message); // Log the message to the report
    }

//        String className = result.getTestClass().getName(); // Get the class name
//        String methodName = result.getMethod().getMethodName(); // Get the method name
//        // Create test with both class and method names
//        extentTest.set(extent.createTest(className + " - " + methodName));
//    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS,"Test PASSED");
    }
    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test failed: {}", result.getName());

        // Ensure WebDriver is not null before attempting to take a screenshot
        if (driver != null) {
            String screenshotPath = "";
            try {
                screenshotPath = getScreenshot(result.getName(), driver);
                logger.info("Screenshot taken: {}", screenshotPath);

                // Attach the screenshot to ExtentReports
                ExtentTest test = extent.createTest(result.getName());
                test.fail("Test failed").addScreenCaptureFromPath(screenshotPath);

                // Capture exception details
                Throwable throwable = result.getThrowable();
                if (throwable != null) {
                    test.fail("Test failed due to: " + throwable.getMessage());
                    test.fail("Stack Trace: " + Arrays.toString(throwable.getStackTrace()));
                }
            } catch (IOException e) {
                logger.error("Error while taking screenshot: {}", e.getMessage());
            }
        } else {
            logger.error("WebDriver is null, cannot take screenshot for failed test: {}", result.getName());
        }
    }
//@Override
//public void onTestFailure(ITestResult result) {
//    logger.error("Test failed: {}", result.getName());
//
//    // Ensure WebDriver is not null before attempting to take a screenshot
//    if (driver != null) {
//        String screenshotPath = "";
//        try {
//            screenshotPath = getScreenshot(result.getName(), driver);
//            logger.info("Screenshot taken: {}", screenshotPath);
//
//            // Attach the screenshot to ExtentReports
//
//            ExtentTest test = extent.createTest(result.getName());
//            test.fail("Test failed").addScreenCaptureFromPath(screenshotPath);
//        } catch (IOException e) {
//            logger.error("Error while taking screenshot: {}", e.getMessage());
//        }
//    } else {
//        logger.error("WebDriver is null, cannot take screenshot for failed test: {}", result.getName());
//    }
//}

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        String message = "All Tests Finished: " + iTestContext.getName();
        extent.createTest("Summary").info(message);
        extent.flush();

    }
}
