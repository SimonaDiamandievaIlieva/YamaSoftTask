package pages;

import core.UserActions;
import org.openqa.selenium.By;

public class TripBuilderPage {

    private final UserActions actions;
    private final AutoSuggestHelper autoSuggest;

    public TripBuilderPage(UserActions actions) {
        this.actions = actions;
        this.autoSuggest = new AutoSuggestHelper(actions);
    }

    private final By originInput =
            By.xpath("//input[@id='origin']");

    private final By waypointItems =
            By.xpath("//div[@class='waypoint-list']/div");

    private final By launchButton =
            By.xpath("//button/div[text()='Launch trip']");

    public void addWaypoint(String text, String city) {
        autoSuggest.select(originInput, text, city);
    }

    public void clickLaunchTrip() {
        actions.waitForClickable(launchButton).click();
    }

    public int getWaypointsCount() {
        return actions.getDriver()
                .findElements(waypointItems)
                .size();
    }

    public boolean isWaypointCityPresent(String city) {

        String waypointCityText =
                "//div[contains(@class,'waypoint')]//*[contains(normalize-space(.),'%s')]";

        By locator = By.xpath(String.format(waypointCityText, city));

        actions.waitForPresent(locator);

        return !actions.getDriver()
                .findElements(locator)
                .isEmpty();
    }
}