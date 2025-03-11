package com.trendy.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndProductId(Long userId, Long productId);

	Optional<Like> findByProductId(Long productId);

	long countByProductId(Long productId);

	void deleteByProductId(Long productId);
}



