package org.example.controller;

import org.example.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShopController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/products/{productDto}")
    public ResponseEntity<String> getProductByProductName(@PathVariable String productDto) {
        Product product = mongoTemplate.findOne(Query.query(Criteria.where("name").is(productDto)), Product.class);
        if (product != null) {
            return ResponseEntity.ok(product.getName() + " costs " + product.getPrice() + " euro");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/product")
    public ResponseEntity<String> addProduct(@RequestBody Product productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());

        Product alreadyExists = mongoTemplate.findOne(Query.query(Criteria.where("name").is(product.getName())), Product.class);
        if (alreadyExists != null && !alreadyExists.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Product already exists");
        } else {
            mongoTemplate.save(product, "products");
            return ResponseEntity.ok("Product added to collection: products");
        }
    }
}
