package com.trendy.admin.stock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminStockRepository extends JpaRepository<AdminStock, Long> {
    Page<AdminStock> findByModelIdContainingOrBrandContaining(
            String modelId, String brand, Pageable pageable);
}
