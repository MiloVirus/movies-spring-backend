package com.unir.products.controller;

import java.util.*;

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

	/*@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts(@RequestHeader Map<String, String> headers) {

		log.info("headers: {}", headers);
		List<Product> products = service.getProducts();

		if (products != null) {
			return ResponseEntity.ok(products);
		} else {
			return ResponseEntity.ok(Collections.emptyList());
		}
	}*/

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
			// If no parameters provided, return all products
			List<Product> allProducts = service.getProducts();
			return ResponseEntity.ok(allProducts);
		} else {
			// Otherwise, filter products based on provided parameters
			List<Product> filteredProducts = service.getProductsByParameters(params);
			return ResponseEntity.ok(filteredProducts);
		}
	}

	private boolean matchesFilter(Product product, Map<String, String> params) {
		for (Map.Entry<String, String> entry : params.entrySet()) {
			String paramKey = entry.getKey();
			String paramValue = entry.getValue();

			// Implement your logic to check if the product matches the filter criteria
			// For example, if the parameter key is "director", check if the product's director matches the provided value
			// Similarly, you can implement logic for other parameters such as category, price range, etc.

			// Example: Check if product director matches the provided director parameter
			if ("director".equalsIgnoreCase(paramKey) && !product.getDirector().equalsIgnoreCase(paramValue)) {
				return false;
			}

			// Add more conditions for other parameters as needed
		}

		// If the product matches all filter criteria, return true
		return true;
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

}

