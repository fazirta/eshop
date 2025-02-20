package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
    }

    private Product buildProduct(String id, String name, int quantity) {
        Product product = new Product();
        product.setProductId(id);
        product.setProductName(name);
        product.setProductQuantity(quantity);
        return product;
    }

    @Test
    @DisplayName("Should create a product and return it")
    void shouldCreateProduct() {
        Product product = buildProduct("p1", "Test Product", 0);
        when(productRepository.create(any(Product.class))).thenReturn(product);

        Product result = productService.create(product);

        verify(productRepository, times(1)).create(product);
        assertNotNull(result, "Created product should not be null");
        assertEquals("p1", result.getProductId(), "Product ID should match");
        assertEquals("Test Product", result.getProductName(), "Product name should match");
    }

    @Test
    @DisplayName("Should return all products from the repository")
    void shouldReturnAllProducts() {
        Product product1 = buildProduct("p1", "Product 1", 10);
        Product product2 = buildProduct("p2", "Product 2", 20);
        List<Product> productList = Arrays.asList(product1, product2);
        Iterator<Product> productIterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(productIterator);

        List<Product> result = productService.findAll();

        verify(productRepository, times(1)).findAll();
        assertEquals(2, result.size(), "Should return exactly 2 products");
        assertEquals("p1", result.get(0).getProductId());
        assertEquals("p2", result.get(1).getProductId());
    }

    @Test
    @DisplayName("Should find a product by its ID")
    void shouldFindProductById() {
        Product product = buildProduct("p1", "Test Product", 5);
        when(productRepository.findById("p1")).thenReturn(product);

        Product result = productService.findById("p1");

        verify(productRepository, times(1)).findById("p1");
        assertNotNull(result, "Found product should not be null");
        assertEquals("p1", result.getProductId());
        assertEquals("Test Product", result.getProductName());
        assertEquals(5, result.getProductQuantity());
    }

    @Test
    @DisplayName("Should update a product and return the updated object")
    void shouldUpdateProduct() {
        Product product = buildProduct("p1", "Updated Product", 50);
        when(productRepository.update(product)).thenReturn(product);

        Product result = productService.update(product);

        verify(productRepository, times(1)).update(product);
        assertNotNull(result, "Updated product should not be null");
        assertEquals("p1", result.getProductId());
        assertEquals("Updated Product", result.getProductName());
        assertEquals(50, result.getProductQuantity());
    }

    @Test
    @DisplayName("Should delete a product by ID and return it")
    void shouldDeleteProduct() {
        Product product = buildProduct("p1", "To Be Deleted", 0);
        when(productRepository.delete("p1")).thenReturn(product);

        Product result = productService.delete("p1");

        verify(productRepository, times(1)).delete("p1");
        assertNotNull(result, "Deleted product should not be null");
        assertEquals("p1", result.getProductId());
        assertEquals("To Be Deleted", result.getProductName());
    }
}
