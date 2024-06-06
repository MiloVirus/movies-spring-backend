package com.unir.products.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.unir.products.data.ProductRepository;
import com.unir.products.model.pojo.Product;
import com.unir.products.model.request.CreateProductRequest;

@Service
public class ProductsServiceImpl implements ProductsService {

	@Autowired
	private ProductRepository repository;

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
	public List<Product> getProductsByDirector(String director) {
		return null;
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

}
