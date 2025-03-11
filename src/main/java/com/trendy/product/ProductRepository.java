package com.trendy.product;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String name);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.likeCount = p.likeCount + 1 WHERE p.id = :productId")
    void incrementLikeCount(@Param("productId") Long productId);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.likeCount = p.likeCount - 1 WHERE p.id = :productId AND p.likeCount > 0")
    void decrementLikeCount(@Param("productId") Long productId);

    @Query("SELECT p.likeCount FROM Product p WHERE p.id = :productId")
    int getLikeCount(@Param("productId") Long productId);

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.productOptions")
    List<Product> findAllWithOptions();
}
