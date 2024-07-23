package org.example.redisexample;

import java.util.List;

import org.example.redisexample.dto.request.ProductRequest;
import org.example.redisexample.entity.Product;
import org.example.redisexample.service.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableCaching
@RestController
@RequestMapping({"/products", "/products/"})
@RequiredArgsConstructor
public class RedisExampleApplication {

    private final ProductService productService;

    //
    @PostMapping
    public Product save(@RequestBody ProductRequest productRequest) {
        return productService.save(productRequest);
    }

    @GetMapping
    public List<Product> getAllProducts() throws JsonMappingException, JsonProcessingException {
        return productService.findAll();
    }

    @GetMapping({ "/{id}", "/{id}/" })
    public Product findProduct(@PathVariable int id) {
        return productService.findProductById(id);
    }

    @DeleteMapping({ "/{id}", "/{id}/" })
    public String deleteProduct(@PathVariable int id) {
        return productService.deleteProduct(id);
    }
    
    public static void main(String[] args) {
        SpringApplication.run(RedisExampleApplication.class, args);
    }
}
