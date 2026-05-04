package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import testComponents.BaseTest;
import testComponents.Retry;


public class CreateNewTripTests extends BaseTest {


    @Test(groups = {"smoke"}, retryAnalyzer = Retry.class)
    public void successfulTripCreation() {
        registerNewUser();
        homePage.clickStartButton();

        whereAreYouGoingPage.selectStartingPoint("okl", statingPoint);
        whereAreYouGoingPage.selectDestination("aus", finalDestination);
        whereAreYouGoingPage.selectStartDate(startDate);
        whereAreYouGoingPage.selectEndDate(endDate);
        whereAreYouGoingPage.clickCreateTripButton();

        tripBuilderPage.clickLaunchTrip();
        Assert.assertTrue(tripBuilderPage.isWaypointCityPresent(statingPoint), "Missing waypoint: " + statingPoint);
        Assert.assertTrue(tripBuilderPage.isWaypointCityPresent(finalDestination), "Missing waypoint: " + finalDestination);
    }

    @Test(groups = {"smoke"}, retryAnalyzer = Retry.class)
    public void successfulTripCreation_withMultipleWaypoints() {
        registerNewUser();
        homePage.clickStartButton();

        whereAreYouGoingPage.selectStartingPoint("okl", statingPoint);
        whereAreYouGoingPage.selectDestination("aus", finalDestination);

        whereAreYouGoingPage.selectStartDate(startDate);
        whereAreYouGoingPage.selectEndDate(endDate);
        whereAreYouGoingPage.clickCreateTripButton();

        tripBuilderPage.addWaypoint("ut", firstWaypoint);
        tripBuilderPage.addWaypoint("co", secondWaypoint);
        tripBuilderPage.addWaypoint("vir", thirdWaypoint);

        Assert.assertEquals(tripBuilderPage.getWaypointsCount(), 5);

        tripBuilderPage.clickLaunchTrip();

        Assert.assertTrue(tripBuilderPage.isWaypointCityPresent(statingPoint), "Missing waypoint: " + statingPoint);
        Assert.assertTrue(tripBuilderPage.isWaypointCityPresent(finalDestination), "Missing waypoint: " + finalDestination);
        Assert.assertTrue(tripBuilderPage.isWaypointCityPresent(firstWaypoint), "Missing waypoint: " + firstWaypoint);
        Assert.assertTrue(tripBuilderPage.isWaypointCityPresent(secondWaypoint), "Missing waypoint: " + secondWaypoint);
        Assert.assertTrue(tripBuilderPage.isWaypointCityPresent(thirdWaypoint), "Missing waypoint: " + thirdWaypoint);
    }

}