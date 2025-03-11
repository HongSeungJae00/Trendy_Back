package com.trendy.admin.quality;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminQualityDTO {

    private Long productId;
    private String productName;
    private Integer price;
    private Integer quantity;
    private String imageUrl;
    private String imageDetailUrl1;
    private String imageDetailUrl2;
    private String imageDetailUrl3;
    private String modelId;
    private String warrantyGrade;
    private String region;
    private String brand;
    private String color;
    private String size;
    private String gender;
    private Integer likeCount;
    private String createdBy;
    private Integer stockQuantity;
    private Boolean isDeleted;
    private String checkPerson;
    private String state;
    private String isArrived;
    private String createdAt;
    private String updatedAt;
}
