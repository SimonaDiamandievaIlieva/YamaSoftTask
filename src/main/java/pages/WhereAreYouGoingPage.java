package pages;

import core.UserActions;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.util.List;
import java.time.temporal.ChronoUnit;

public class WhereAreYouGoingPage {

    private final UserActions actions;
    private final AutoSuggestHelper autoSuggest;

    public WhereAreYouGoingPage(UserActions actions) {
        this.actions = actions;
        this.autoSuggest = new AutoSuggestHelper(actions);
    }

    private final By startingPointInput = By.id("origin");
    private final By destinationInput = By.id("destination");

    private final By startDateInput = By.id("start_date");
    private final By endDateInput = By.id("end_date");

    private final By nextMonthBtn = By.xpath("//button[@aria-label='Next Month']");
    private final By prevMonthBtn = By.xpath("//button[@aria-label='Previous Month']");
    private final By allDays = By.xpath("//button[contains(@class,'MuiButtonBase-root')]");

    private final By createTripButton =
            By.xpath("//button[contains(@class,'primary-filled')]");

    public void selectStartingPoint(String text, String city) {
        autoSuggest.select(startingPointInput, text, city);
    }

    public void selectDestination(String text, String city) {
        autoSuggest.select(destinationInput, text, city);
    }

    public void selectStartDate(LocalDate targetDate) {
        selectDate(startDateInput, targetDate);
    }

    public void selectEndDate(LocalDate targetDate) {
        selectDate(endDateInput, targetDate);
    }

    private void selectDate(By input, LocalDate targetDate) {

        actions.waitForClickable(input).click();

        navigateToMonth(targetDate);

        selectDay(targetDate.getDayOfMonth());
    }

    private void navigateToMonth(LocalDate targetDate) {

        LocalDate today = LocalDate.now();

        long diff = ChronoUnit.MONTHS.between(
                today.withDayOfMonth(1),
                targetDate.withDayOfMonth(1)
        );

        for (int i = 0; i < Math.abs(diff); i++) {
            if (diff > 0) {
                actions.waitForClickable(nextMonthBtn).click();
            } else {
                actions.waitForClickable(prevMonthBtn).click();
            }
        }
    }

    private void selectDay(int day) {

        List<WebElement> days =
                actions.waitForVisibilityOfAllElements(allDays);

        for (WebElement el : days) {
            try {
                if (el.isDisplayed()
                        && el.isEnabled()
                        && el.getText().equals(String.valueOf(day))) {
                    el.click();
                    return;
                }
            } catch (StaleElementReferenceException ignored) {
            }
        }

        throw new RuntimeException("Day not found: " + day);
    }

    public void clickCreateTripButton() {
        actions.click(createTripButton);
    }

    public boolean isOriginFilled() {
        return isFieldFilled(startingPointInput);
    }

    public boolean isDestinationFilled() {
        return isFieldFilled(destinationInput);
    }

    private boolean isFieldFilled(By locator) {
        try {
            WebElement el = actions.getDriver().findElement(locator);
            String cls = el.getAttribute("class");
            return cls != null && cls.contains("filled");
        } catch (StaleElementReferenceException e) {
            return false;
        }
    }

    public boolean isCreateTripButtonDisplayed() {
        return actions.getDriver().findElement(createTripButton).isDisplayed();
    }
}