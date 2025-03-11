package com.trendy.admin.quality;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminQualityRepository extends JpaRepository<AdminQuality, Long> {

    // 상품 ID로 검색
    Optional<AdminQuality> findByProductId(Long productId);
    
    @Query("SELECT p FROM AdminQuality p WHERE p.productId = :productId")
    List<AdminQuality> findByProductIdAsList(@Param("productId") Long productId);

    // 상품 이름으로 검색 (부분 일치)
    List<AdminQuality> findByProductNameContaining(String productName);

    // 브랜드로 검색
    //List<AdminProduct> findByBrand(AdminProduct.Brand brand);

    // 모든 상품을 상품 ID 기준 내림차순 정렬
    List<AdminQuality> findAllByOrderByProductIdDesc();

    // 상품 ID로 삭제
    void deleteByProductId(Long productId);
    
    // 다중 상품 삭제
    void deleteAllByProductIdIn(List<Long> productIds);
    
    @Query("SELECT DISTINCT p.color FROM AdminQuality p WHERE p.color IS NOT NULL")
    List<String> findDistinctColors();

    @Query("SELECT DISTINCT p.size FROM AdminQuality p WHERE p.size IS NOT NULL")
    List<String> findDistinctSizes();

    @Query("SELECT DISTINCT p.isArrived FROM AdminQuality p WHERE p.isArrived IS NOT NULL")
    List<String> findDistinctIsArriveds();

    @Query("SELECT DISTINCT p.state FROM AdminQuality p WHERE p.state IS NOT NULL")
    List<String> findDistinctStates();

    @Query("SELECT DISTINCT p.warrantyGrade FROM AdminQuality p WHERE p.warrantyGrade IS NOT NULL")
    List<String> findDistinctWarrantyGrades();

    @Query("SELECT DISTINCT p.createdBy FROM AdminQuality p WHERE p.createdBy IS NOT NULL")
    List<String> findDistinctCreatedBys();
}
