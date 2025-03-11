package com.trendy.mypage.resell;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trendy.login.User;

@Repository
public interface SalesDataRepository extends JpaRepository<SalesData, Long> {
    List<SalesData> findByUserId(Long userId);
    List<SalesData> findByModelId(String modelId);
    Optional<SalesData> findByProductOptionProductIdAndUserId(Long productId, Long userId);
} 