package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportersNG {
    private static ExtentReports extent;

    public static ExtentReports getReportObject() {
            String path = System.getProperty("user.dir") + "\\reports\\index.html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(path);
            reporter.config().setReportName("Web Bugaboo Automation results");
            reporter.config().setDocumentTitle("Test results");

            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Tester", "Tamara Lazarovska");
            extent.createTest(path);
            return extent;
        }
    }



//    public static ExtentReports getReportObject(){
//        String path = System.getProperty("user.dir") + "\\reports\\index.html";
//        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
//        reporter.config().setReportName("Web Bugaboo Automation results");
//        reporter.config().setDocumentTitle("Test results");
//
//        ExtentReports extent = new ExtentReports();
//        extent.attachReporter(reporter);
//        extent.setSystemInfo("Tester","Tamara Lazarovska");
//        extent.createTest(path);
//        return extent;
//    }
//}
