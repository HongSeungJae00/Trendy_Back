package com.trendy.admin.stock;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminWarehouseRepository extends JpaRepository<AdminWarehouse, Long> {
    // 기본 CRUD 메서드는 JpaRepository에서 제공
    
    // 지역으로 창고 찾기
    Optional<AdminWarehouse> findByRegion(String region);
    
    // 재고량으로 창고 찾기
    List<AdminWarehouse> findByStockGreaterThan(Integer stock);
    
    // 관리자 이름으로 창고 찾기
    List<AdminWarehouse> findByManagerNameContaining(String managerName);
}