package com.unir.products.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.unir.products.model.pojo.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {


}
