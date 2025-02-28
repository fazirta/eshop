package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    private Product buildProduct(String id, String name, int quantity) {
        Product product = new Product();
        product.setProductId(id);
        product.setProductName(name);
        product.setProductQuantity(quantity);
        return product;
    }

    @Test
    @DisplayName("Should display createProduct page with an empty product model")
    void shouldDisplayCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    @DisplayName("Should create a product and redirect to product list")
    void shouldCreateProductAndRedirect() throws Exception {
        Product mockProduct = buildProduct("p1", "Test Product", 10);
        when(productService.create(ArgumentMatchers.any(Product.class))).thenReturn(mockProduct);

        mockMvc.perform(post("/product/create")
                        .param("productId", "p1")
                        .param("productName", "Test Product")
                        .param("productQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(productService, times(1)).create(ArgumentMatchers.any(Product.class));
    }

    @Test
    @DisplayName("Should display productList page with products in model")
    void shouldDisplayProductListPage() throws Exception {
        Product p1 = buildProduct("p1", "Product 1", 5);
        List<Product> products = new ArrayList<>();
        products.add(p1);

        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attribute("products", products));

        verify(productService, times(1)).findAll();
    }

    @Test
    @DisplayName("Should display editProduct page when product is found")
    void shouldDisplayEditProductPageWhenFound() throws Exception {
        Product mockProduct = buildProduct("p1", "Some Product", 10);
        when(productService.findById("p1")).thenReturn(mockProduct);

        mockMvc.perform(get("/product/edit/p1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attribute("product", mockProduct));

        verify(productService, times(1)).findById("p1");
    }

    @Test
    @DisplayName("Should redirect to product list when product is not found for editing")
    void shouldRedirectWhenEditProductNotFound() throws Exception {
        when(productService.findById("p2")).thenReturn(null);

        mockMvc.perform(get("/product/edit/p2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(productService, times(1)).findById("p2");
    }

    @Test
    @DisplayName("Should update a product and redirect to product list")
    void shouldUpdateProductAndRedirect() throws Exception {
        Product mockProduct = buildProduct("p1", "New Name", 50);
        when(productService.update(ArgumentMatchers.any(Product.class))).thenReturn(mockProduct);

        mockMvc.perform(post("/product/edit/p1")
                        .param("productName", "New Name")
                        .param("productQuantity", "50"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(productService, times(1)).update(ArgumentMatchers.any(Product.class));
    }

    @Test
    @DisplayName("Should delete a product and redirect to product list")
    void shouldDeleteProductAndRedirect() throws Exception {
        Product deletedProduct = buildProduct("p1", "Deleted Product", 0);
        when(productService.delete("p1")).thenReturn(deletedProduct);

        mockMvc.perform(get("/product/delete/p1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(productService, times(1)).delete("p1");
    }
}
