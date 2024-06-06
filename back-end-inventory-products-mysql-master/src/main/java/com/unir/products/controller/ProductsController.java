package com.unir.products.controller;

import java.util.*;

import com.unir.products.model.pojo.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unir.products.model.pojo.Product;
import com.unir.products.model.request.CreateProductRequest;
import com.unir.products.service.ProductsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductsController {

	private final ProductsService service;

	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable String productId) {

		log.info("Request received for product {}", productId);
		Product product = service.getProduct(productId);

		if (product != null) {
			return ResponseEntity.ok(product);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@GetMapping(value = "/products")
	public ResponseEntity<List<Product>> getProducts(@RequestParam Map<String, String> params) {
		if (params.isEmpty()) {
			List<Product> allProducts = service.getProducts();
			return ResponseEntity.ok(allProducts);
		} else {
			List<Product> filteredProducts = service.getProductsByParameters(params);
			return ResponseEntity.ok(filteredProducts);
		}
	}
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {

		Boolean removed = service.removeProduct(productId);

		if (Boolean.TRUE.equals(removed)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@PostMapping("/products")
	public ResponseEntity<Product> getProduct(@RequestBody CreateProductRequest request) {

		Product createdProduct = service.createProduct(request);

		if (createdProduct != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
		} else {
			return ResponseEntity.badRequest().build();
		}

	}

	public ResponseEntity<Product> updateProduct(@PathVariable String productId, @RequestBody ProductDto body)
	{
		Product updated = service.updateProduct(productId, body);
		if(updated != null)
		{
			return ResponseEntity.ok(updated);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

}

