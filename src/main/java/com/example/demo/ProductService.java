package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
    public List<Product> listAll(){
        return repository.findAll(Sort.by("email").ascending());
    }
    public void SaveProduct(Product product){
        repository.save(product);
    }
    public void DeleteProduct(int id){
        repository.deleteById(id);
    }
    public Product get(int id){
        return repository.findById(id).get();
    }
}
