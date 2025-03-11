package com.trendy.product;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private String modelId;
    private int price;
    private String imageUrl;
    private String imageDetailUrl1;
    private String imageDetailUrl2;
    private String imageDetailUrl3;
    private String color;
    private String warrantyGrade; // ENUM as String
    private String region; // ENUM as String
    private String brand; // ENUM as String
    private String gender; // ENUM as String ("male", "female")
    private int likeCount;
    private String createdBy; // ENUM as String ("admin", "seller")
    private String state; // ENUM as String ("before", "ing", "complete")
    private String isArrived; // ENUM as String ("arrival", "nonarrival")
    private List<String> availableSizes;
    private List<ProductOptionDTO> productOptions;
    private Integer quantity = 1;

    
    public ProductDTO(Long id, String brand, String name, int price, int likeCount) {
    	this.id = id;
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.likeCount = likeCount;
    }
    
    public ProductDTO(Long id, String brand, String name, int price, String imageUrl, String modelId,int likeCount) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.modelId = modelId;
        this.likeCount = likeCount;
    }

    public ProductDTO(Long id, String brand, String name, int price, String imageUrl, String imageDetailUrl1, String imageDetailUrl2, 
                        String imageDetailUrl3, String modelId, String color, String gender, int likeCount, String createdBy) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.imageDetailUrl1 = imageDetailUrl1;
        this.imageDetailUrl2 = imageDetailUrl2;
        this.imageDetailUrl3 = imageDetailUrl3;
        this.modelId = modelId;
        this.color = color;
        this.gender = gender;
        this.likeCount = likeCount;
        this.createdBy = createdBy;
    }


}
