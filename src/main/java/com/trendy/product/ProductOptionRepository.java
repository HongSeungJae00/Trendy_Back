package com.trendy.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    
    // 특정 상품의 모든 옵션 조회
    List<ProductOption> findByProductId(Long productId);
    
    // 특정 상품의 특정 사이즈 옵션 조회
    Optional<ProductOption> findByProductIdAndSize(Long productId, ProductOption.Size size);
    
    // 재고가 있는 옵션만 조회
    @Query("SELECT po FROM ProductOption po WHERE po.product.id = :productId AND po.stockQuantity > 0 AND po.isAvailable = true")
    List<ProductOption> findAvailableOptions(@Param("productId") Long productId);
    
    // 특정 상품의 특정 사이즈 재고 확인
    @Query("SELECT po.stockQuantity FROM ProductOption po WHERE po.product.id = :productId AND po.size = :size")
    Integer getStockQuantity(@Param("productId") Long productId, @Param("size") ProductOption.Size size);
    
    // 재고 업데이트
    @Query("UPDATE ProductOption po SET po.stockQuantity = :quantity WHERE po.id = :optionId")
    void updateStockQuantity(@Param("optionId") Long optionId, @Param("quantity") int quantity);
} 