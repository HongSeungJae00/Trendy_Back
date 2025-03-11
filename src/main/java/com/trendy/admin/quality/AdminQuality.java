package com.trendy.admin.quality;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminQuality implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "quantity", columnDefinition = "int default 1")
    private Integer quantity;

    @Column(name = "image_url", nullable = false, length = 1000)
    private String imageUrl;

    @Column(name = "image_detail_url1", length = 1000)
    private String imageDetailUrl1;

    @Column(name = "image_detail_url2", length = 1000)
    private String imageDetailUrl2;

    @Column(name = "image_detail_url3", length = 1000)
    private String imageDetailUrl3;

    @Column(name = "model_id", nullable = false, length = 255)
    private String modelId;

    @Enumerated(EnumType.STRING)
    @Column(name = "warranty_grade")
    private WarrantyGrade warrantyGrade;

    @Enumerated(EnumType.STRING)
    @Column(name = "region")
    private Region region;

    @Enumerated(EnumType.STRING)
    @Column(name = "brand", nullable = false)
    private Brand brand;

    @Column(name = "color", length = 50)
    private String color;

    @Column(name = "size")
    private String size;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "like_count", columnDefinition = "int default 0")
    private Integer likeCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "created_by", nullable = false)
    private CreatedBy createdBy;
    
    @Column(name = "check_person", length = 50)
    private String checkPerson;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", columnDefinition = "ENUM('before', 'ing', 'complete') DEFAULT 'before'")
    private State state;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_Arrived", columnDefinition = "ENUM('arrival', 'nonarrival')")
    private IsArrived isArrived;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    public enum WarrantyGrade {
        A, B, C
    }

    public enum Region {
        Mapo, Gangnam, Jamsil
    }

    public enum Brand {
        Nike, Adidas, Asics, New_Balance, Reebok, Others
    }
/*
    public enum Size {
        _230, _240, _250, _260, _270, _280, _290
    }
*/
    public enum Gender {
        male, female
    }

    public enum CreatedBy {
        admin, seller
    }

    public enum State {
        before, ing, complete
    }

    public enum IsArrived {
        arrival, nonarrival
    }
}
