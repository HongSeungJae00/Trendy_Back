package com.trendy.admin.main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMainStockRepository extends JpaRepository<AdminMainStock, Long> {
    List<AdminMainStock> findAllByOrderByStockIdDesc();  // id -> stockId로 변경
}