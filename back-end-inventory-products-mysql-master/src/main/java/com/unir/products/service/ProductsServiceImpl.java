package com.unir.products.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.unir.products.data.ProductRepository;
import com.unir.products.model.pojo.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.unir.products.model.pojo.Product;
import com.unir.products.model.request.CreateProductRequest;

@Service
@Slf4j
public class ProductsServiceImpl implements ProductsService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public List<Product> getProducts() {

		List<Product> products = repository.findAll();
		return products.isEmpty() ? null : products;
	}

	private boolean matchesFilter(Product product, Map<String, String> params) {
		for (Map.Entry<String, String> entry : params.entrySet()) {
			String paramKey = entry.getKey();
			String paramValue = entry.getValue();
			switch (paramKey) {
				case "name":

					if (!product.getName().toLowerCase().contains(paramValue.toLowerCase())) {
						return false;
					}
					break;
				case "description":

					if (!product.getDescription().toLowerCase().contains(paramValue.toLowerCase())) {
						return false;
					}
					break;
				case "director":

					if (!product.getDirector().equalsIgnoreCase(paramValue)) {
						return false;
					}
					break;
				case "year":

					if (product.getYear().equalsIgnoreCase(paramValue)) {
						return false;
					}
					break;
				case "critiques":

					if (product.getCritiques().equalsIgnoreCase(paramValue)) {
						return false;
					}
					break;
				case "length":

					if (product.getLength().equalsIgnoreCase(paramValue)) {
						return false;
					}
					break;
				case "category":

					if (!product.getCategory().equalsIgnoreCase(paramValue)) {
						return false;
					}
					break;
				case "stars":
					if (product.getStars().equalsIgnoreCase(paramValue)) {
						return false;
					}
					break;
				case "visible":

					if (product.getVisible() != Boolean.parseBoolean(paramValue)) {
						return false;
					}
					break;
				default:
					break;
			}
		}
		return true;
	}
	@Override
	public List<Product> getProductsByParameters(Map<String, String> params) {
		List<Product> filteredProducts = new ArrayList<>();
		List<Product> allProducts = repository.findAll();

		for (Product product : allProducts) {
			if (matchesFilter(product, params)) {
				filteredProducts.add(product);
			}
		}

		return filteredProducts;
	}

	@Override
	public Product getProduct(String productId) {
		return repository.findById(Long.valueOf(productId)).orElse(null);
	}

	@Override
	public Boolean removeProduct(String productId) {

		Product product = repository.findById(Long.valueOf(productId)).orElse(null);

		if (product != null) {
			repository.delete(product);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public Product createProduct(CreateProductRequest request) {

		if (request != null && StringUtils.hasLength(request.getName().trim())
				&& StringUtils.hasLength(request.getDescription().trim())
				&& request.getVisible() != null) {

			Product product = Product.builder().name(request.getName()).description(request.getDescription())
					.director(request.getDirector()).year(request.getYear()).critiques(request.getCritiques())
					.length(request.getLength()).category(request.getCategory())
					.stars(request.getStars()).visible(request.getVisible()).build();

			return repository.save(product);
		} else {
			return null;
		}
	}


	@Override
	public Product updateProduct(String productId, String updateRequest) {
		//PATCH se implementa en este caso mediante Merge Patch: https://datatracker.ietf.org/doc/html/rfc7386
		Product product = repository.getById(Long.valueOf(productId));
		if (product != null) {
			try {
				JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(updateRequest));
				JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(product)));
				Product patched = objectMapper.treeToValue(target, Product.class);
				repository.save(patched);
				return patched;
			} catch (JsonProcessingException | JsonPatchException e) {
				log.error("Error updating product {}", productId, e);
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public Product updateProduct(String productId, ProductDto updateRequest) {
		Product product = repository.getById(Long.valueOf(productId));
		if (product != null) {
			product.update(updateRequest);
			repository.save(product);
			return product;
		} else {
			return null;
		}
	}

}
