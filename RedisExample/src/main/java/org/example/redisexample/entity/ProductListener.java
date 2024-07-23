package org.example.redisexample.entity;

import org.example.redisexample.service.ProductRedisService;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ProductListener {
    private final ProductRedisService productRedisService;
    
    @PrePersist //save == persist
    public void prePersist(Product product){
        log.info("pre persist");
    }
    
    @PostPersist
    public void postPersist(Product product){
        log.info("post persist");
        // clear cache
        productRedisService.clear();
    }
    
    @PreUpdate
    public void preUpdate(Product product){
        log.info("pre update");
    }
    
    @PostUpdate
    public void postUpdate(Product product){
        log.info("post update");
        // clear cache
        productRedisService.clear();
    }
    
    @PreRemove
    public void preRemove(Product product){
        log.info("pre remove");
    }
    
    @PostRemove
    public void postRemove(Product product){
        log.info("post remove");
        // clear cache
        productRedisService.clear();
    }
}
