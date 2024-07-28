package org.example.redisexample.service;

import java.util.List;

import org.example.redisexample.dto.request.ProductRequest;
import org.example.redisexample.entity.Product;
import org.example.redisexample.repository.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.RequiredArgsConstructor;

//@Repository
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductRedisService productRedisService;
    @SuppressWarnings("unused")
    private final RedisService redisService;
    
    public static final String HASH_KEY = "Product";
    
    
    public Product save(ProductRequest product) {
        // redisTemplate.opsForHash().put(HASH_KEY, product.getId(), product);
        Product _product = Product.builder().name(product.getName()).price(product.getPrice()).build();
        _product = productRepository.save(_product);
        System.out.println(_product);
        // redisService.hashSet(_product.getId() + "", HASH_KEY, _product);
        // redisService.set(_product.getId() + "", _product);
        return _product;
    }
    
    // @Cacheable(value = "products")
    public List<Product> findAll() throws JsonMappingException, JsonProcessingException{
        System.out.println("Fetching all products..." + " " + productRepository.hashCode());
        if(productRedisService.getAllProduct() == null){
            List<Product> products = productRepository.findAll();
            productRedisService.saveAllProducts(products);
            return products;
        }
        return productRedisService.getAllProduct();
        // redisTemplate.opsForHash().values(HASH_KEY).stream().map(o -> (Product)o).collect(Collectors.toList());
    }
    
    @Cacheable(value = "products", key = "#id")
    public Product findProductById(int id) {
        System.out.println("Fetching product by id..." + " " + productRepository.hashCode());
        return productRepository.findById(id).get(); 
        // (Product) redisTemplate.opsForHash().get(HASH_KEY, id);
    }
    
    public String deleteProduct(int id){
        // redisTemplate.opsForHash().delete(HASH_KEY, id);
        return "Product removed!";
    }
}
