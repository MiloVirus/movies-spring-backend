package com.unir.products.service;

import java.util.List;
import java.util.Optional;

import com.unir.products.model.pojo.Product;
import com.unir.products.model.request.CreateProductRequest;

public interface ProductsService {

	List<Product> getProducts();

	Product getProduct(String productId);

	List<Product> getProductsByDirector(String director);

	Boolean removeProduct(String productId);

	Product createProduct(CreateProductRequest request);
}