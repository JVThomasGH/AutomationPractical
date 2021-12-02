package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class TestCases {

    private WebDriver driver;
    String baseUrl = "http://automationpractice.com";

    @FindBy(css = "#search_query_top")
    private WebElement searchField;

    @FindBy(css = "[name='submit_search']")
    private WebElement searchButton;

    @FindBy(css = ".lighter")
    private WebElement searchHeading;

    @FindBy(css = ".product-container .right-block")
    private List<WebElement> searchResults;

    @FindBy(css = ".login")
    private WebElement linkSignIn;

    @FindBy(css = ".logout")
    private WebElement linkSignOut;

    @FindBy(css = "#email")
    private WebElement inputEmail;

    @FindBy(css = "#passwd")
    private WebElement inputPassword;

    @FindBy(css = "#SubmitLogin > span")
    private WebElement buttonSignIn;

    @FindBy(css = "[name='Submit'] > span")
    private WebElement buttonAddCart;

    @FindBy(css = "[data-title='Unit price'] > .price > .price")
    private WebElement unitPrice;

    @FindBy(css = "[data-title='Total'] > .price")
    private WebElement totalCost;

    @FindBy(css = ".icon-minus")
    private WebElement decreaseUnit;

    @FindBy(css = ".icon-plus")
    private WebElement increaseUnit;

    @FindBy(css = "[title='Proceed to checkout'] > span")
    private WebElement proceedToCheckoutPopUp;

    @FindBy(css = ".ajax_add_to_cart_button > span")
    private WebElement addToCartPopUp;

    @FindBy(css = ".cross")
    private WebElement addToCartPopUpClose;

    @FindBy(css = "[title='View my shopping cart']")
    private WebElement buttonCart;

    @FindBy(css = ".standard-checkout")
    private WebElement proceedToCheckout;

    @FindBy(css = ".cart_quantity_input")
    private WebElement amountDisplayed;

    @FindBy(css = "#cgv")
    private WebElement tncCheckBox;

    @FindBy(xpath = "//span[.='Proceed to checkout']")
    private WebElement button2ProceedToCheckout;

    @FindBy(xpath = "//button[@name='processCarrier']/span[contains(.,'Proceed to checkout')]")
    private WebElement button3ProceedToCheckout;

    @FindBy(xpath = ".cat-name")
    private WebElement labelPageName;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        PageFactory.initElements(driver, this);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get(baseUrl);
    }

    String searchHeadingText;
    String searchItem;
    String searchResultsText;
    String data;
    public String username = "jodyvthomas@gmail.com";
    public String password = "77245";

    @Test
    public void TC1() {
//        TC1 - Navigate to http://automationpractice.com/ perform a search.
        searchItem = "T-SHIRT";
        searchField.clear();
        searchField.sendKeys(searchItem);
        searchButton.click();
        searchHeadingText = searchHeading.getText();
        searchResultsText = searchResults.get(0).getText();

//        Verify the first result matches your search criteria.
        Assert.assertTrue(searchHeadingText.contains(searchItem));
        Assert.assertTrue(searchResultsText.toLowerCase().contains(searchItem.toLowerCase()), "NO SEARCH RESULTS FOUND");
    }

    @Test
    public void TC2() {
//        TC2 a - Store 3 search criteria in one variable separated by commas.
        ArrayList<String> itemList = new ArrayList<>();
        String searchItems = "T-SHIRT, DRESS, BLOUSE";
        String[] split = searchItems.split(", ");
        for (String s : split) {
            System.out.println("ITEM TO SEARCH: " + s);
            itemList.add(s);
        }

//        TC2 b - Manipulate the string and store each search criteria in an array and use a loop to perform the search and verify.
        for (String item : itemList) {
            searchField.clear();
            searchField.sendKeys(item);
            searchButton.click();

            searchHeadingText = searchHeading.getText();
            searchResultsText = searchResults.get(0).getText();

            //        Verify the first result matches your search criteria.
            Assert.assertTrue(searchHeadingText.contains(item));
            Assert.assertTrue(searchResultsText.toLowerCase().contains(item.toLowerCase()), "NO SEARCH RESULTS FOUND");
        }
    }


    @Test
    public void TC3() {
//        TC3 -  Data driven approach using an external text file.

//        Opening data.txt file and retrieving data
        try {
            File myObj = new File("src/main/resources/data.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        searchItem = data;
        searchField.clear();
        searchField.sendKeys(data);
        searchButton.click();
        searchHeadingText = searchHeading.getText();
        searchResultsText = searchResults.get(0).getText();

//        Verify the first result matches your search criteria.
        Assert.assertTrue(searchHeadingText.contains(searchItem));
        Assert.assertTrue(searchResultsText.toLowerCase().contains(searchItem.toLowerCase()), "NO SEARCH RESULTS FOUND");
    }

    @Test
    public void TC4() {
//        TC4 – Sign in to the website using a username and password stored as a global variable.
        linkSignIn.click();
        inputEmail.sendKeys(username);
        inputPassword.sendKeys(password);
        buttonSignIn.click();
        linkSignOut.click();
    }

    public void numberOfUnits(int quantity) throws InterruptedException {
        for (int i = 0; i < quantity - 1; i++) {
            increaseUnit.click();
            Thread.sleep(200);
        }
        Thread.sleep(10000);
    }

    public int calculateTotal(int unitPrice, int quantity) {
        return unitPrice * quantity;
    }

    @Test
    public void TC5() throws InterruptedException {
        Actions action = new Actions(driver);
//        TC5 – Sign in to the website and Search for item
        int quantity = 7;
        searchItem = "T-SHIRT";
        linkSignIn.click();
        inputEmail.sendKeys(username);
        inputPassword.sendKeys(password);
        buttonSignIn.click();
        searchField.clear();
        searchField.sendKeys(searchItem);
        searchButton.click();

//        TC5 a - Add an item to shopping your cart.
        action.moveToElement(searchResults.get(0)).moveToElement(addToCartPopUp).click().perform();
        addToCartPopUpClose.click();

//        TC5 b -  View shopping cart.
        buttonCart.click();

//        TC5 c - Increase quantity to a desired amount.
        numberOfUnits(quantity);

//        TC5 d - Calculate total (unit price x quantity)
        int uPrice = Integer.parseInt(unitPrice.getText().replace("$", "").replace(".", ""));
        int tPrice = Integer.parseInt(totalCost.getText().replace("$", "").replace(".", ""));

        int totalCost = calculateTotal(uPrice, quantity);
        System.out.println(totalCost);

//        TC5 e - Verify displayed total matches calculated total.
        Assert.assertEquals(totalCost, tPrice, "PRICE MISMATCH");

        linkSignOut.click();
    }

    public void selectMenu(String menu, String subMenu) {
        Actions action = new Actions(driver);
        if (menu.equalsIgnoreCase("Dresses")) {
            WebElement tabMenu = driver.findElement(By.cssSelector(".sf-menu > li > [title='Dresses']"));
            WebElement tabSubMenu = driver.findElement(By.cssSelector(".sf-menu .submenu-container > li > [title='" + subMenu + "']"));
            action.moveToElement(tabMenu).moveToElement(tabSubMenu).click().perform();
        } else {
            WebElement tabMenu = driver.findElement(By.cssSelector(".sf-with-ul[title='" + menu + "']"));
            WebElement tabSubMenu = driver.findElement(By.cssSelector(".sf-menu .submenu-container ul [title='" + subMenu + "']"));
            action.moveToElement(tabMenu).moveToElement(tabSubMenu).click().perform();
        }
        Assert.assertTrue(driver.getTitle().contains(subMenu), "MENU AND PAGE TITLE MISMATCH");
    }

    @Test
    public void TC6() {
//        TC6 – Create a generic test case that uses the Navigation Menu to navigate to a certain page.
        linkSignIn.click();
        inputEmail.sendKeys(username);
        inputPassword.sendKeys(password);
        buttonSignIn.click();
        selectMenu("Dresses", "Summer Dresses");

    }

    @AfterClass
    public void tearDown() throws InterruptedException {
        if (driver != null) {
            driver.quit();
            System.out.println("DONE, CLEANING UP...");
        }
    }
}
