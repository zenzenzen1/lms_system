package com.example.kafka_example.entity;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Product implements Serializable{
    private int id;
    private String name;
}
