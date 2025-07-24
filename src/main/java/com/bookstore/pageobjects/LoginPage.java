package com.bookstore.pageobjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.bookstore.actiondriver.Actions;

public class LoginPage {

	private Actions action;
    WebDriver driver;
    private By enterusername = By.xpath("//input[@id='userName']");
    private By enterpassword = By.xpath("//input[@id='password']");
    private By loginButton = By.xpath("//button[@id='login']");
    private By errorMessage = By.xpath("//p[contains(text(),'Invalid username or password!')]");
    private By profileHeader = By.xpath("//label[@id='userName-value']"); 


    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.action = new Actions(driver);
    }

    // Actions
    public void enterUserName(String userName) {
        action.sendKeys(enterusername, userName);
    }

    public void enterPassword(String password) {
        action.sendKeys(enterpassword, password);
    }

    public void clickLogin() {
        action.scrollToElement(loginButton);
        action.waitForElementToBeClickable(loginButton);
        action.click(loginButton);
    }

    public void login(String userName, String password) {
        enterUserName(userName);
        enterPassword(password);
        clickLogin();
    }

    public String getErrorMessage() {
        action.waitForElement(errorMessage);
        return action.getElementText(errorMessage);
    }

    public boolean isLoginSuccessful() {
        return action.isElementDisplayed(profileHeader);
    }

    public String getLoggedInUser() {
        return action.getElementText(profileHeader);
    }
}