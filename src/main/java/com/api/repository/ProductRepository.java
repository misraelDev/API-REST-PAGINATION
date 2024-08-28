package com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
}