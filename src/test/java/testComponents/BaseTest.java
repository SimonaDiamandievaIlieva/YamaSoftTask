package testComponents;

import com.github.javafaker.Faker;
import core.SetUpWebDriver;
import core.UserActions;
import core.Utils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.*;

import java.time.LocalDate;

public class BaseTest {

    protected WebDriver driver;
    protected UserActions actions;
    protected Faker faker;

    protected String username;
    protected String email;
    protected String password;

    protected RegistrationPage reg;
    protected HomePage homePage;
    protected WhereAreYouGoingPage whereAreYouGoingPage;
    protected TripBuilderPage tripBuilderPage;

    protected LocalDate startDate = LocalDate.now().plusDays(1);
    protected LocalDate endDate = startDate.plusDays(5);

    protected String statingPoint = "Oklahoma City";
    protected String finalDestination = "Austell";
    protected String firstWaypoint = "Utah";
    protected String secondWaypoint = "Colorado";
    protected String thirdWaypoint = "Virginia";

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        driver = SetUpWebDriver.getDriver();
        driver.manage().deleteAllCookies();
        driver.get(Utils.getConfigPropertyByKey("base.url"));

        actions = new UserActions(driver, Utils.getTimeout());
        faker = new Faker();

        username = faker.name().username();
        email = faker.internet().emailAddress();
        password = "Test123!";

        homePage = new HomePage(actions);
        reg = new RegistrationPage(actions);
        whereAreYouGoingPage = new WhereAreYouGoingPage(actions);
        tripBuilderPage = new TripBuilderPage(actions);

        waitForPageReady();
        homePage.cleanPage();
    }


    protected void registerNewUser() {
        reg.openRegistration();
        reg.register(username, email, password);
    }

    private void waitForPageReady() {
        actions.getWait().until(d ->
                ((org.openqa.selenium.JavascriptExecutor) d)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        SetUpWebDriver.quitDriver();
    }
}