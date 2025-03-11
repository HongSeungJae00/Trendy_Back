// package com.trendy.admin.product;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;

// @Repository
// public interface AdminProductRepository extends JpaRepository<AdminProduct, Long> {

//     // 상품 ID로 검색
//     Optional<AdminProduct> findByProductId(Long productId);
    
//     @Query("SELECT p FROM AdminProduct p WHERE p.productId = :productId")
//     List<AdminProduct> findByProductIdAsList(@Param("productId") Long productId);

//     // 상품 이름으로 검색 (부분 일치)
//     List<AdminProduct> findByProductNameContaining(String productName);

//     // 브랜드로 검색
//     //List<AdminProduct> findByBrand(AdminProduct.Brand brand);

//     // 모든 상품을 상품 ID 기준 내림차순 정렬
//     List<AdminProduct> findAllByOrderByProductIdDesc();

//     // 상품 ID로 삭제
//     void deleteByProductId(Long productId);
    
//     // 다중 상품 삭제
//     void deleteAllByProductIdIn(List<Long> productIds);
    
//     @Query("SELECT DISTINCT p.brand FROM AdminProduct p")
//     List<String> findDistinctBrands();

//     @Query("SELECT DISTINCT p.color FROM AdminProduct p")
//     List<String> findDistinctColors();

//     @Query("SELECT DISTINCT p.size FROM AdminProduct p")
//     List<String> findDistinctSizes();

//     @Query("SELECT DISTINCT p.gender FROM AdminProduct p")
//     List<String> findDistinctGenders();
    
//     @Query("SELECT p FROM AdminProduct p WHERE p.brand = :brand")
//     List<AdminProduct> findByBrand(@Param("brand") AdminProduct.Brand brand);
// }
