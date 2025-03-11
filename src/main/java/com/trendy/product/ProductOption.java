package com.trendy.product;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "product_options")
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Convert(converter = SizeConverter.class)
    @Column(nullable = false)
    private Size size;

    @Column(name = "stock_quantity", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int stockQuantity;

    @Column(name = "is_available", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isAvailable;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isAvailable = true;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum Size {
        SIZE_230("230"),
        SIZE_240("240"),
        SIZE_250("250"),
        SIZE_260("260"),
        SIZE_270("270"),
        SIZE_280("280"),
        SIZE_290("290");

        private final String value;

        Size(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Size fromValue(String value) {
            for (Size size : Size.values()) {
                if (size.value.equals(value)) {
                    return size;
                }
            }
            return null;
        }
    }
} 