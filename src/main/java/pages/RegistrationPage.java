package pages;

import core.UserActions;
import org.openqa.selenium.By;

public class RegistrationPage {

    private final UserActions actions;

    public RegistrationPage(UserActions actions) {
        this.actions = actions;
    }

    private final By loginButton =
            By.xpath("//a[@data-sweetchuck-id='top-bar__button--log-in']");

    private final By createAccountButton =
            By.xpath("//button[@data-sweetchuck-id='login-form__button--register-footer']");

    private final By usernameField = By.xpath("//input[@id='username']");
    private final By emailField = By.xpath("//input[@id='email']");
    private final By passwordField = By.xpath("//input[@id='password']");

    private final By signUpButton =
            By.xpath("//button[@form='register-form']");

    private final By successPopupButton =
            By.cssSelector(".rt-button.primary-filled.large.default");

    private final By closePopUpButton =
            By.xpath("//button[@data-sweetchuck-id='modal__button--close']");

    public void openRegistration() {
        openLogin();
        actions.waitForVisible(createAccountButton);
        actions.click(createAccountButton);
    }

    public void register(String username, String email, String password) {
        actions.type(usernameField, username);
        actions.type(emailField, email);
        actions.type(passwordField, password);

        actions.click(signUpButton);

        closeSuccessPopup();
        closeFinalPopup();
    }

    public void openLogin() {
        actions.waitForClickable(loginButton);
        actions.click(loginButton);
    }

    public void closeSuccessPopup() {
        actions.click(successPopupButton);
    }

    public void closeFinalPopup() {
        actions.click(closePopUpButton);
    }
}