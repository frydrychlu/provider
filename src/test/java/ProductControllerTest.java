import org.example.controller.ProductController;
import org.example.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private ProductController productController;

    @Test
    void getProductByProductName_ReturnsOkResponse_WhenProductExists() {
        // Arrange
        Product product = new Product("TestProduct", 10);
        when(mongoTemplate.findOne(any(Query.class), eq(Product.class))).thenReturn(product);

        // Act
        ResponseEntity<String> response = productController.getProductByProductName("TestProduct");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("TestProduct costs 10 euro", response.getBody());
    }

    @Test
    void getProductByProductName_ReturnsNotFoundResponse_WhenProductDoesNotExist() {
        // Arrange
        when(mongoTemplate.findOne(any(Query.class), eq(Product.class))).thenReturn(null);

        // Act
        ResponseEntity<String> response = productController.getProductByProductName("NonExistingProduct");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}