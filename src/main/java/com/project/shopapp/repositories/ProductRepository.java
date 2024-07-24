package com.project.shopapp.repositories;

import com.project.shopapp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long>{
    boolean existsByName(String name);
    Page<Product> findAll(Pageable pageable);
}
