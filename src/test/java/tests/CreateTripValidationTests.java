package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import testComponents.BaseTest;
import testComponents.Retry;

public class CreateTripValidationTests extends BaseTest {

    @Test(groups = {"smoke"}, retryAnalyzer = Retry.class)
    public void createTrip_withOnlyStartingPoint_shouldMarkDestinationInvalid() {
        registerNewUser();
        homePage.clickStartButton();
        whereAreYouGoingPage.selectStartingPoint("okl", statingPoint);
        whereAreYouGoingPage.clickCreateTripButton();

        Assert.assertTrue(whereAreYouGoingPage.isOriginFilled(),
                "Origin should be marked as filled");

        Assert.assertFalse(whereAreYouGoingPage.isDestinationFilled(),
                "Destination should NOT be marked as filled (should show validation error)");

        Assert.assertTrue(whereAreYouGoingPage.isCreateTripButtonDisplayed());
    }

    @Test(groups = {"smoke"}, retryAnalyzer = Retry.class)
    public void createTrip_withOnlyDestinationPoint_shouldMarkStartingPointInvalid() {
        registerNewUser();
        homePage.clickStartButton();
        whereAreYouGoingPage.selectDestination("okl", statingPoint);
        whereAreYouGoingPage.clickCreateTripButton();

        Assert.assertTrue(whereAreYouGoingPage.isDestinationFilled(),
                "Destination should be marked as filled");

        Assert.assertFalse(whereAreYouGoingPage.isOriginFilled(),
                "Starting point should NOT be marked as filled (should show validation error)");

        Assert.assertTrue(whereAreYouGoingPage.isCreateTripButtonDisplayed());
    }
}
