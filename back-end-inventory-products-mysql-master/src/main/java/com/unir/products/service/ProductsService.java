package com.unir.products.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.unir.products.model.pojo.Product;
import com.unir.products.model.pojo.ProductDto;
import com.unir.products.model.request.CreateProductRequest;

public interface ProductsService {

	List<Product> getProducts();

	List<Product> getProductsByParameters(Map<String, String> params);

	Product getProduct(String productId);

	Boolean removeProduct(String productId);

	Product createProduct(CreateProductRequest request);

	Product updateProduct(String productId, String updateRequest);

	Product updateProduct(String productId, ProductDto updateRequest);

}