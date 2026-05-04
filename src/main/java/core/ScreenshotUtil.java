package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScreenshotUtil {

    public static String getScreenshot(String methodName, WebDriver driver) throws IOException {
        String fileName = methodName + "_" + System.currentTimeMillis() + ".png";
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destination = "reports/screenshots/" + fileName;
        Files.copy(screenshot.toPath(), Paths.get(destination));
        return destination;
    }
}