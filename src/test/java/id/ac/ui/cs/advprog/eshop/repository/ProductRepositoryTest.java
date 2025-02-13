package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {
    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {}

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd7");
        product2.setProductName("Sampo Cap Bambang");
        product2.setProductQuantity(100);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProductPositive() {
        Product product = new Product();
        product.setProductId("uuid-1");
        product.setProductName("Old Name");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("uuid-1");
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(200);

        Product result = productRepository.update(updatedProduct);

        assertNotNull(result, "Updated product should not be null");
        assertEquals("uuid-1", result.getProductId(), "Product ID should remain unchanged");
        assertEquals("New Name", result.getProductName(), "Product name should be updated");
        assertEquals(200, result.getProductQuantity(), "Product quantity should be updated");
    }

    @Test
    void testEditProductNegativeNonExistent() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-existent-uuid");
        updatedProduct.setProductName("Should Not Update");
        updatedProduct.setProductQuantity(-50);

        Product result = productRepository.update(updatedProduct);
        assertNull(result, "Updating a non-existent product should return null");
    }

    @Test
    void testDeleteProductPositive() {
        Product product = new Product();
        product.setProductId("uuid-2");
        product.setProductName("Product To Delete");
        product.setProductQuantity(150);
        productRepository.create(product);

        Product deleted = productRepository.delete("uuid-2");
        assertNotNull(deleted, "Deleted product should be returned");
        assertEquals("uuid-2", deleted.getProductId(), "Deleted product's ID should match");

        Iterator<Product> iterator = productRepository.findAll();
        while (iterator.hasNext()) {
            Product p = iterator.next();
            assertNotEquals("uuid-2", p.getProductId(), "Deleted product should no longer be in the repository");
        }
    }

    @Test
    void testDeleteProductNegativeNonExistent() {
        Product deleted = productRepository.delete("non-existent-id");
        assertNull(deleted, "Deleting a non-existent product should return null");
    }
}
