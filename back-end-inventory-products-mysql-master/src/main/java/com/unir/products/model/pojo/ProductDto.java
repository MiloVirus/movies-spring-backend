package com.unir.products.model.pojo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDto {

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
