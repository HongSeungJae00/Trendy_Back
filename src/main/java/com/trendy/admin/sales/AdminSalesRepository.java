package com.trendy.admin.sales;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminSalesRepository extends JpaRepository<AdminSales, Long> {
    List<AdminSales> findByModelIdContaining(String keyword);
}