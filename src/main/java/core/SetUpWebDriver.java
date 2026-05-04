package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class SetUpWebDriver {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            driver.set(createDriver());
            driver.get().manage().window().maximize();
        }
        return driver.get();
    }

    private static WebDriver createDriver() {

        String browser = System.getProperty("browser",
                Utils.getConfigPropertyByKey("browser"));

        boolean isHeadless = Boolean.parseBoolean(
                System.getProperty("headless",
                        Utils.getConfigPropertyByKey("headless"))
        );

        switch (browser.toLowerCase()) {

            case "chrome":
                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();

                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--window-size=1920,1080");
                    chromeOptions.addArguments("--force-device-scale-factor=1");
                }

                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--remote-allow-origins=*");

                return new ChromeDriver(chromeOptions);

            case "firefox":
                WebDriverManager.firefoxdriver().setup();

                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (isHeadless) {
                    firefoxOptions.addArguments("-headless");
                }

                firefoxOptions.addArguments("--width=1920");
                firefoxOptions.addArguments("--height=1080");

                return new FirefoxDriver(firefoxOptions);

            case "edge":
                WebDriverManager.edgedriver().setup();

                EdgeOptions edgeOptions = new EdgeOptions();

                if (isHeadless) {
                    edgeOptions.addArguments("--headless=new");
                    edgeOptions.addArguments("--window-size=1920,1080");
                }

                edgeOptions.addArguments("--disable-gpu");
                edgeOptions.addArguments("--no-sandbox");

                return new EdgeDriver(edgeOptions);

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    public static void quitDriver() {
        WebDriver currentDriver = driver.get();

        if (currentDriver != null) {
            currentDriver.quit();
            driver.remove();
        }
    }
}