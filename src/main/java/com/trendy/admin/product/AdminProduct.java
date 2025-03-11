// package com.trendy.admin.product;

// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;

// import jakarta.persistence.CascadeType;
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.EnumType;
// import jakarta.persistence.Enumerated;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.PrePersist;
// import jakarta.persistence.PreUpdate;
// import jakarta.persistence.Table;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// import com.fasterxml.jackson.annotation.JsonIgnore;

// @Getter
// @Setter
// @Entity
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder(toBuilder = true)
// @Table(name = "products")
// public class AdminProduct {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "product_id")
//     private Long id;

//     @Column(name = "product_name", nullable = false)
//     private String name;

//     @Column(nullable = false)
//     private int price;

//     @Column(name = "image_url", nullable = false)
//     private String imageUrl;

//     @Column(name = "image_detail_url1")
//     private String imageDetailUrl1;

//     @Column(name = "image_detail_url2")
//     private String imageDetailUrl2;

//     @Column(name = "image_detail_url3")
//     private String imageDetailUrl3;

//     @Column(name = "model_id", nullable = false)
//     private String modelId;

//     @Enumerated(EnumType.STRING)
//     @Column(name = "warranty_grade")
//     private WarrantyGrade warrantyGrade;

//     @Column(name = "color")
//     private String color;

//     @Enumerated(EnumType.STRING)
//     private Region region;

//     @Enumerated(EnumType.STRING)
//     @Column(nullable = false)
//     private Brand brand;

//     @Enumerated(EnumType.STRING)
//     private Gender gender;

//     @Column(name = "like_count", columnDefinition = "INT DEFAULT 0")
//     private int likeCount;

//     @Enumerated(EnumType.STRING)
//     @Column(name = "created_by")
//     private CreatedBy createdBy;

//     @Enumerated(EnumType.STRING)
//     @Column(columnDefinition = "ENUM('before', 'ing', 'complete') DEFAULT 'before'")
//     private State state;

//     @Enumerated(EnumType.STRING)
//     @Column(name = "is_arrived")
//     private ArrivalStatus isArrived;

//     @Column(name = "created_at", nullable = false, updatable = false)
//     private LocalDateTime createdAt;

//     @Column(name = "updated_at", nullable = false)
//     private LocalDateTime updatedAt;

//     @OneToMany(mappedBy = "product")
//     @JsonIgnore
//     private List<AdminProductOption> productOptions = new ArrayList<>();

//     @PrePersist
//     protected void onCreate() {
//         this.createdAt = LocalDateTime.now();
//         this.updatedAt = LocalDateTime.now();
//         this.state = State.before;
//     }

//     @PreUpdate
//     protected void onUpdate() {
//         this.updatedAt = LocalDateTime.now();
//     }

//     public AdminProduct(Long productId) {
// 		this.id = productId;
// 	}

//     public enum Brand {
//         Nike, Adidas, Asics, New_Balance, Reebok, Others
//     }

//     public enum WarrantyGrade {
//         A, B, C
//     }

//     public enum Region {
//         Mapo, Gangnam, Jamsil
//     }

//     public enum Gender {
//         male, female
//     }

//     public enum CreatedBy {
//         admin, seller
//     }
    
//     public enum State {
//         before, ing, complete
//     }

//     public enum ArrivalStatus {
//         arrival, nonarrival
//     }
// }
