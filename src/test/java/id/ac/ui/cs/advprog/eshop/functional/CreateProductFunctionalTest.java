package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setUpTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");
        String title = driver.getTitle();
        assertEquals("Create New Product", title, "The page title should be 'Create New Product'");
    }

    @Test
    void form_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");

        WebElement header = driver.findElement(By.tagName("h3"));
        assertEquals("Create New Product", header.getText(), "Header should be 'Create New Product'");

        WebElement form = driver.findElement(By.tagName("form"));
        assertNotNull(form, "The form should be present on the page");

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        assertNotNull(nameInput, "The name input field should be present");
        String namePlaceholder = nameInput.getAttribute("placeholder");
        assertEquals("Enter product' name", namePlaceholder, "Name input placeholder should be 'Enter product' name'");

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        assertNotNull(quantityInput, "The quantity input field should be present");
        String quantityPlaceholder = quantityInput.getAttribute("placeholder");
        assertEquals("Enter product' name", quantityPlaceholder, "Quantity input placeholder should be 'Enter product' name'");

        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        assertNotNull(submitButton, "The submit button should be present");
        assertEquals("Submit", submitButton.getText(), "The submit button should have the text 'Submit'");
    }

    @Test
    public void createProduct_isSuccessful(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.sendKeys("Samsung Galaxy S20 Ultra");

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.sendKeys("128");

        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        assertTrue(driver.getCurrentUrl().contains("/product/list"),
                "After creation, user should be redirected to the product list page.");

        WebElement productNameCell = driver.findElement(By.xpath("//td[text()='Samsung Galaxy S20 Ultra']"));
        assertNotNull(productNameCell, "Newly created product name should be visible in the list.");

        WebElement quantityCell = driver.findElement(By.xpath("//td[text()='128']"));
        assertNotNull(quantityCell, "Newly created product quantity should be visible in the list.");
    }
}
