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
public class EditProductFunctionalTest {

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
    public void testEditProduct_isSuccessful(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.sendKeys("Original Product");
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.sendKeys("10");
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        assertTrue(driver.getCurrentUrl().contains("/product/list"),
                "After creation, user should be redirected to the product list page.");

        WebElement editLink = driver.findElement(By.xpath("//tr[td[text()='Original Product']]//a[contains(@href, '/product/edit/')]"));
        editLink.click();

        WebElement header = driver.findElement(By.tagName("h3"));
        assertEquals("Edit Product", header.getText(), "The header should be 'Edit Product'");

        WebElement nameEditInput = driver.findElement(By.id("nameInput"));
        nameEditInput.clear();
        nameEditInput.sendKeys("Updated Product");

        WebElement quantityEditInput = driver.findElement(By.id("quantityInput"));
        quantityEditInput.clear();
        quantityEditInput.sendKeys("20");

        WebElement updateButton = driver.findElement(By.xpath("//button[@type='submit']"));
        updateButton.click();

        assertTrue(driver.getCurrentUrl().contains("/product/list"),
                "After updating, user should be redirected to the product list page.");

        WebElement updatedProductName = driver.findElement(By.xpath("//td[text()='Updated Product']"));
        assertNotNull(updatedProductName, "Updated product name should be visible in the list.");

        WebElement updatedQuantity = driver.findElement(By.xpath("//td[text()='20']"));
        assertNotNull(updatedQuantity, "Updated product quantity should be visible in the list.");
    }

    @Test
    public void testEditProduct_negativeNonExistent(ChromeDriver driver) {
        String fakeId = "non-existent-uuid";
        driver.get(baseUrl + "/product/edit/" + fakeId);

        assertTrue(driver.getCurrentUrl().contains("/product/list"),
                "A non-existent product should redirect the user to the product list page.");
    }
}
