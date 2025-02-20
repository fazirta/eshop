package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    private List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEachRemaining(products::add);
        return products;
    }

    private Product buildProduct(String id, String name, int quantity) {
        Product product = new Product();
        product.setProductId(id);
        product.setProductName(name);
        product.setProductQuantity(quantity);
        return product;
    }

    @Test
    @DisplayName("Should create a product and find it in the repository")
    void shouldCreateAndFindProduct() {
        final Product product = buildProduct("eb558e9f-1c39-460e-8860-71af6af63bd6", "Sampo Cap Bambang", 100);

        final Product created = productRepository.create(product);
        final Iterator<Product> productIterator = productRepository.findAll();

        assertNotNull(created, "Created product should not be null");
        assertTrue(productIterator.hasNext(), "Repository should have at least one product");

        final Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId(), "IDs should match");
        assertEquals(product.getProductName(), savedProduct.getProductName(), "Names should match");
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity(), "Quantities should match");
        assertFalse(productIterator.hasNext(), "Repository should contain exactly one product");
    }

    @Test
    @DisplayName("Should find an existing product by ID")
    void shouldFindExistingProductById() {
        final Product product = buildProduct("uuid-find-1", "Product Find Test", 999);
        final Product created = productRepository.create(product);
        assertNotNull(created);

        final Product found = productRepository.findById("uuid-find-1");

        assertNotNull(found, "Found product should not be null");
        assertEquals(created.getProductId(), found.getProductId());
        assertEquals(created.getProductName(), found.getProductName());
        assertEquals(created.getProductQuantity(), found.getProductQuantity());
    }

    @Test
    @DisplayName("Should return null when finding a non-existent product by ID")
    void shouldReturnNullForNonExistentProductId() {
        final Product found = productRepository.findById("non-existent-id");

        assertNull(found, "Expected null for a non-existent product");
    }

    @Test
    @DisplayName("Should return empty iterator when no products exist")
    void shouldReturnEmptyIteratorIfRepositoryEmpty() {
        final Iterator<Product> productIterator = productRepository.findAll();

        assertFalse(productIterator.hasNext(), "Repository should be empty initially");
    }

    @Test
    @DisplayName("Should find all products when multiple products exist")
    void shouldFindAllWhenMultipleProductsExist() {
        final Product product1 = buildProduct("p1", "Sampo Cap Bambang 1", 100);
        final Product product2 = buildProduct("p2", "Sampo Cap Bambang 2", 200);
        productRepository.create(product1);
        productRepository.create(product2);

        final Iterator<Product> productIterator = productRepository.findAll();

        assertTrue(productIterator.hasNext(), "Expected at least one product");
        final Product savedProduct1 = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct1.getProductId());
        assertEquals(product1.getProductName(), savedProduct1.getProductName());
        assertEquals(product1.getProductQuantity(), savedProduct1.getProductQuantity());

        assertTrue(productIterator.hasNext(), "Expected a second product");
        final Product savedProduct2 = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct2.getProductId());
        assertEquals(product2.getProductName(), savedProduct2.getProductName());
        assertEquals(product2.getProductQuantity(), savedProduct2.getProductQuantity());

        assertFalse(productIterator.hasNext(), "No more products expected");
    }

    @Test
    @DisplayName("Should update an existing product successfully")
    void shouldUpdateExistingProduct() {
        final Product product = buildProduct("uuid-1", "Old Name", 100);
        productRepository.create(product);

        final Product updatedProduct = buildProduct("uuid-1", "New Name", 200);

        final Product result = productRepository.update(updatedProduct);

        assertNotNull(result, "Updated product should not be null");
        assertEquals("uuid-1", result.getProductId());
        assertEquals("New Name", result.getProductName());
        assertEquals(200, result.getProductQuantity());

        final Product found = productRepository.findById("uuid-1");
        assertNotNull(found, "Product should still exist after update");
        assertEquals("New Name", found.getProductName());
        assertEquals(200, found.getProductQuantity());
    }

    @Test
    @DisplayName("Should return null when updating a non-existent product")
    void shouldReturnNullWhenUpdatingNonExistentProduct() {
        final Product updatedProduct = buildProduct("non-existent-uuid", "Should Not Update", -50);

        final Product result = productRepository.update(updatedProduct);

        assertNull(result, "Updating a non-existent product should return null");
        assertTrue(getAllProducts().isEmpty(), "Repository should remain empty since no product was added");
    }

    @Test
    @DisplayName("Should update the first product in a multiple-product repository")
    void shouldUpdateFirstProductInMultiple() {
        final Product product1 = buildProduct("p1", "Product 1", 10);
        final Product product2 = buildProduct("p2", "Product 2", 20);
        productRepository.create(product1);
        productRepository.create(product2);

        final Product updateP1 = buildProduct("p1", "Updated Product 1", 100);

        final Product result = productRepository.update(updateP1);

        assertNotNull(result, "Should successfully update the first product");
        assertEquals("p1", result.getProductId());
        assertEquals("Updated Product 1", result.getProductName());
        assertEquals(100, result.getProductQuantity());

        final Product stillP2 = productRepository.findById("p2");
        assertNotNull(stillP2);
        assertEquals("Product 2", stillP2.getProductName());
        assertEquals(20, stillP2.getProductQuantity());
    }

    @Test
    @DisplayName("Should update the second product in a multiple-product repository")
    void shouldUpdateSecondProductInMultiple() {
        final Product product1 = buildProduct("p1", "Product 1", 10);
        final Product product2 = buildProduct("p2", "Product 2", 20);
        productRepository.create(product1);
        productRepository.create(product2);

        final Product updateP2 = buildProduct("p2", "Updated Product 2", 200);

        final Product result = productRepository.update(updateP2);

        assertNotNull(result, "Should successfully update the second product");
        assertEquals("p2", result.getProductId());
        assertEquals("Updated Product 2", result.getProductName());
        assertEquals(200, result.getProductQuantity());

        final Product stillP1 = productRepository.findById("p1");
        assertNotNull(stillP1);
        assertEquals("Product 1", stillP1.getProductName());
        assertEquals(10, stillP1.getProductQuantity());
    }

    @Test
    @DisplayName("Should delete an existing product successfully")
    void shouldDeleteExistingProduct() {
        final Product product = buildProduct("uuid-2", "Product To Delete", 150);
        productRepository.create(product);

        final Product deleted = productRepository.delete("uuid-2");

        assertNotNull(deleted, "Deleted product should be returned");
        assertEquals("uuid-2", deleted.getProductId());
        assertEquals("Product To Delete", deleted.getProductName());
        assertEquals(150, deleted.getProductQuantity());

        assertNull(productRepository.findById("uuid-2"), "Product should no longer exist in the repository");
    }

    @Test
    @DisplayName("Should return null when deleting a non-existent product")
    void shouldReturnNullWhenDeletingNonExistentProduct() {
        final Product deleted = productRepository.delete("non-existent-id");

        assertNull(deleted, "Deleting a non-existent product should return null");
        assertTrue(getAllProducts().isEmpty(), "Repository should remain empty");
    }

    @Test
    @DisplayName("Should delete the first product in a multiple-product repository")
    void shouldDeleteFirstProductInMultiple() {
        final Product product1 = buildProduct("p1", "Product 1", 10);
        final Product product2 = buildProduct("p2", "Product 2", 20);
        productRepository.create(product1);
        productRepository.create(product2);

        final Product deleted = productRepository.delete("p1");

        assertNotNull(deleted, "Should successfully delete the first product");
        assertEquals("p1", deleted.getProductId());

        final Product remaining = productRepository.findById("p2");
        assertNotNull(remaining, "Second product should remain in the repository");
        assertEquals("p2", remaining.getProductId());
        assertEquals("Product 2", remaining.getProductName());
        assertEquals(20, remaining.getProductQuantity());
    }

    @Test
    @DisplayName("Should delete the second product in a multiple-product repository")
    void shouldDeleteSecondProductInMultiple() {
        final Product product1 = buildProduct("p1", "Product 1", 10);
        final Product product2 = buildProduct("p2", "Product 2", 20);
        productRepository.create(product1);
        productRepository.create(product2);

        final Product deleted = productRepository.delete("p2");

        assertNotNull(deleted, "Should successfully delete the second product");
        assertEquals("p2", deleted.getProductId());
        assertEquals("Product 2", deleted.getProductName());
        assertEquals(20, deleted.getProductQuantity());

        final Product remaining = productRepository.findById("p1");
        assertNotNull(remaining);
        assertEquals("p1", remaining.getProductId());
        assertEquals("Product 1", remaining.getProductName());
        assertEquals(10, remaining.getProductQuantity());
    }

    @Test
    @DisplayName("Should return null if product in repository has null ID and does not match delete ID")
    void shouldReturnNullWhenDeletingProductWithNullIdInRepo() {
        final Product product = buildProduct(null, "Null ID Product", 10);
        productRepository.create(product);

        final Product deleted = productRepository.delete("some-id");

        assertNull(deleted, "Should return null because the product in repo has null ID, not matching 'some-id'");

        final List<Product> allProducts = getAllProducts();
        assertEquals(1, allProducts.size());
        assertNull(allProducts.get(0).getProductId());
    }

    @Test
    @DisplayName("Should return null when delete is called with a null parameter")
    void shouldReturnNullWhenDeletingWithNullParameter() {
        final Product product = buildProduct("non-null-id", "Non-null ID Product", 20);
        productRepository.create(product);

        final Product deleted = productRepository.delete(null);

        assertNull(deleted, "Should return null because we're deleting with a null ID");
        assertNotNull(productRepository.findById("non-null-id"),
                "Original product should still exist in the repository");
    }
}
