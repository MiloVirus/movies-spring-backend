package com.unir.products.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

	private String name;
	private String description;
	private String director;
	private String year;
	private String critiques;
	private String length;
	private String category;
	private String stars;
	private Boolean visible;
}
