package core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class UserActions {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public UserActions(WebDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }


    public WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForPresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public List<WebElement> waitForVisibilityOfAllElements(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public void click(By locator) {

        WebElement element = waitForClickable(locator);

        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});", element);
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", element);
        }
    }

    public void type(By locator, String text) {
        WebElement element = waitForVisible(locator);
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    public void removeIframe(By iframeLocator) {
        try {
            WebElement iframe = waitForPresent(iframeLocator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].remove();", iframe);
        } catch (TimeoutException e) {
            throw new RuntimeException("Failed to remove iframe: " + iframeLocator, e);
        }
    }

    public void removeOverlay(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);

            if (!elements.isEmpty()) {
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].remove();", elements.get(0));
            }

        } catch (TimeoutException e) {
            throw new RuntimeException("Failed to remove overlay: " + locator, e);
        }
    }

    public void removeCookiesBanner(By locator) {
        try {
            WebElement cookies = waitForPresent(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].remove();", cookies);
        } catch (TimeoutException e) {
            throw new RuntimeException("Failed to remove cookies banner: " + locator, e);
        }
    }
}