// package com.example.kafka_example.convertor;

// import java.util.Collections;

// import org.springframework.kafka.support.converter.JsonMessageConverter;
// import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
// import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
// import org.springframework.stereotype.Component;

// import com.example.kafka_example.entity.Product;

// @Component
// public class ProductMessageConvertor extends JsonMessageConverter{
//     public ProductMessageConvertor() {
//         super();
//         DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
//         typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
//         typeMapper.addTrustedPackages("com.example.kafka_example");
//         typeMapper.setIdClassMapping(Collections.singletonMap("product", Product.class));
//     }
// }
