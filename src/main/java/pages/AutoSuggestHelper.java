package pages;

import core.UserActions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class AutoSuggestHelper {

    private final UserActions actions;

    public AutoSuggestHelper(UserActions actions) {
        this.actions = actions;
    }

    private final By options =
            By.xpath("//div[contains(@class,'rt-autocomplete-list')]//div");

    public void select(By inputField, String textToType, String expectedValue) {

        WebElement input = actions.waitForClickable(inputField);
        input.click();

        clearInput(input);

        input.sendKeys(textToType);

        waitForOptionsToBeVisible();

        selectFromDropdown(expectedValue);
    }

    private void waitForOptionsToBeVisible() {

        actions.getWait().until(ExpectedConditions
                .visibilityOfElementLocated(options));

        actions.getWait().until(driver ->
                !driver.findElements(options).isEmpty()
        );
    }

    private void selectFromDropdown(String expectedValue) {

        String expected = expectedValue.trim().toLowerCase();

        List<WebElement> items = actions.getDriver().findElements(options);

        for (WebElement item : items) {
            try {
                String text = item.getText().trim().toLowerCase();

                if (text.contains(expected)) {
                    item.click();
                    return;
                }

            } catch (StaleElementReferenceException e) {
                items = actions.getDriver().findElements(options);
            }
        }

        throw new RuntimeException("Option not found in dropdown: " + expectedValue);
    }

    private void clearInput(WebElement input) {

        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.DELETE);
    }
}

