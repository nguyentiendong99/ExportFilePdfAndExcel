package com.example.demo;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product , Integer> {
    List<Product> findAll(Sort email);
}
