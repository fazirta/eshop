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
public class DeleteProductFunctionalTest {

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
    public void deleteProduct_isSuccessful(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.sendKeys("Product to Delete");

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.sendKeys("5");

        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        assertTrue(driver.getCurrentUrl().contains("/product/list"),
                "After creation, user should be redirected to the product list page.");

        WebElement deleteLink = driver.findElement(By.xpath("//tr[td[text()='Product to Delete']]//a[contains(@href, '/product/delete/')]"));
        deleteLink.click();

        assertTrue(driver.getCurrentUrl().contains("/product/list"),
                "After deletion, user should be redirected to the product list page.");

        int count = driver.findElements(By.xpath("//td[text()='Product to Delete']")).size();
        assertEquals(0, count, "The product should no longer be visible in the product list.");
    }

    @Test
    public void deleteProduct_negativeNonExistent(ChromeDriver driver) {
        String nonExistentId = "non-existent-uuid";
        driver.get(baseUrl + "/product/delete/" + nonExistentId);

        assertTrue(driver.getCurrentUrl().contains("/product/list"),
                "When attempting to delete a non-existent product, user should be redirected to the product list page.");
    }
}
