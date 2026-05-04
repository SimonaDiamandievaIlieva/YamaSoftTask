package pages;

import core.UserActions;
import org.openqa.selenium.By;

public class HomePage {

    private final UserActions actions;

    public HomePage(UserActions actions) {
        this.actions = actions;
    }

    private final By iframePopUp = By.xpath("//iframe[contains(@class, 'gist-message')]");
    private final By cookiesPolicy = By.xpath("//div[@id='onetrust-consent-sdk']");
    private final By gistOverlay = By.id("gist-overlay");

    public void removePopupIframe() {
        try {
            actions.waitForPresent(iframePopUp);
            actions.removeIframe(iframePopUp);
        } catch (Exception ignored) {}
    }

    public void removeCookies() {
        actions.removeCookiesBanner(cookiesPolicy);
    }

    public void removeOverlay() {
        actions.removeOverlay(gistOverlay);
    }

    public void cleanPage() {
        removePopupIframe();
        removeOverlay();
        removeCookies();
    }

    private final By startTripButton =
            By.xpath("//button[@data-id='add-waypoint']");

    public void clickStartButton() {
        actions.click(startTripButton);
    }
}