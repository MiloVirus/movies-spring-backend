package com.unir.products.model.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "director")
	private String director;

	@Column(name = "year")
	private String year;

	@Column(name = "critiques")
	private String critiques;

	@Column(name = "length")
	private String length;

	@Column(name = "category")
	private String category;

	@Column(name = "stars")
	private String stars;

	@Column(name = "visible")
	private Boolean visible;

	public void update(ProductDto productDto) {
		this.name = productDto.getName();
		this.year = productDto.getYear();
		this.description = productDto.getDescription();
		this.critiques = productDto.getCritiques();
		this.length = productDto.getLength();
		this.category = productDto.getCategory();
		this.stars = productDto.getStars();
		this.visible = productDto.getVisible();
		this.director = productDto.getDirector();
	}

}
