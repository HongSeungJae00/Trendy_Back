package com.trendy.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
	int countBySeller_UserIdAndStatus(Long userId, String status);
    List<Sale> findBySeller_UserId(Long userId);

    @Query("SELECT COUNT(s) FROM Sale s WHERE s.seller.userId = :userId AND s.status = :status")
    int countSalesByStatus(@Param("userId") Long userId, @Param("status") String status);
}
