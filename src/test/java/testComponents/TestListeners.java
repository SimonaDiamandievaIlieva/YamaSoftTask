package testComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import core.SetUpWebDriver;
import core.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListeners implements ITestListener {

    ExtentTest test;
    ExtentReports extent = ExtentManager.getExtent();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        extentTest.get().fail(result.getThrowable());

        WebDriver driver = SetUpWebDriver.getDriver();

        String filePath = null;

        try {
            filePath = ScreenshotUtil.getScreenshot(
                    result.getMethod().getMethodName(),
                    driver
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        extentTest.get().addScreenCaptureFromPath(
                filePath,
                result.getMethod().getMethodName()
        );
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        extentTest.remove();
    }
}