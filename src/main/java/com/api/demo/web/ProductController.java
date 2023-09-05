package com.api.demo.web;

import com.api.demo.entities.Product;
import com.api.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    private static final Logger LOGGER = LogManager.getLogger(ProductController.class);
    @Autowired
    ProductService productService;

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllProducts() {
        LOGGER.info("Init GetAllProducts");
        List<Product> listProducts = new ArrayList<Product>();

        try {
          listProducts = productService.findAll();
        }catch (Exception ex){
            LOGGER.error("Error returning list of products: " + ex.getMessage());
            return new ResponseEntity<>(
                    "Error returning list of products: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        LOGGER.info("End GetAllProducts...");
        return new ResponseEntity<>(
                listProducts,
                HttpStatus.OK);

    }

    @GetMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
        LOGGER.info("Init getProductById...");
        Product product = new Product();

        try {
             product = Optional.of(productService.findById(id)).get().orElse(new Product());
        }catch (Exception ex){
            LOGGER.error("Error returning product Data: " + ex.getMessage());
            return new ResponseEntity<>(
                    "Error returning product Data: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        LOGGER.info("End getProductById...");
        return new ResponseEntity<>(
                product,
                HttpStatus.OK);


    }


}
