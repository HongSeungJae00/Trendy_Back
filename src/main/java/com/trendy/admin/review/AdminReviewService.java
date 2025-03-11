package com.trendy.admin.review;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("unused")
public class AdminReviewService {
	@Autowired
	private HttpSession session;
	@Autowired
	private AdminReviewRepository reviewRepository;
	//@Autowired
    //private AdminOrderItemRepository orderItemRepository;

	// [목록 페이지]
	public List<AdminReview> getAllReviews() {
	    List<AdminReview> reviews = reviewRepository.findAllByOrderByReviewIdDesc();
	    reviews.forEach(review -> {
	        System.out.println("Review ID: " + review.getReviewId());
	    });
	    return reviews;
	}
	
	// 주문 상세 정보 조회
    public AdminReview findByReviewId(Long reviewId) {
        return reviewRepository.findByReviewId(reviewId).orElse(null);
    }
    /*
    // Order_Items 조회
    public List<AdminOrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
	*/
	// [수정 페이지]
	// 게시물 조회
	public AdminReview findById(Long reviewId) {
		return reviewRepository.findByReviewId(reviewId).orElse(null);
	}

	// 게시물 수정 처리
	public String updateReview(AdminReviewDTO reviewDTO) {
		System.out.println("여기까지잘됨3");
	    AdminReview review = reviewRepository.findByReviewId(reviewDTO.getReviewId()).orElse(null);
	    if (review == null) {
	        return "주문이 존재하지 않습니다.";
	    }
	    System.out.println("여기까지잘됨4");
	    review.setReviewId(reviewDTO.getReviewId());
	    System.out.println("여기까지잘됨62");
	    review.setProductId(reviewDTO.getProductId());
	    review.setUserId(reviewDTO.getUserId());
	    System.out.println("여기까지잘됨63");
	    review.setContent(reviewDTO.getContent());
	    System.out.println("여기까지잘됨64");
	    reviewRepository.save(review);
	    System.out.println("여기까지잘됨5");
	    return "주문이 성공적으로 수정되었습니다.";
	}


	// 게시물 삭제
	@Transactional
    public void deleteReview(Long reviewId) {
        try {
            reviewRepository.deleteByReviewId(reviewId);
        } catch (Exception e) {
            throw new RuntimeException("삭제 중 오류 발생: " + e.getMessage());
        }
    }
	
	
	// 다중 게시물 삭제
	@Transactional
    public void deleteMultipleReviews(List<Long> reviewIds) {
        try {
            reviewRepository.deleteAllByReviewIdIn(reviewIds);
        } catch (Exception e) {
            throw new RuntimeException("다중 삭제 중 오류 발생: " + e.getMessage());
        }
    }
	
	 // 게시물 등록
	public void registerReview(AdminReviewDTO reviewDTO) {
		System.out.println("여기까지잘됨61");
	    AdminReview review = new AdminReview();
	    System.out.println("여기까지잘됨62");
	    review.setReviewId(reviewDTO.getReviewId());
	    System.out.println("여기까지잘됨62");
	    review.setProductId(reviewDTO.getProductId());
	    review.setUserId(reviewDTO.getUserId());
	    System.out.println("여기까지잘됨63");
	    review.setContent(reviewDTO.getContent());
	    System.out.println("여기까지잘됨64");
	    System.out.println("Set TotalPrice: " + review.getReviewId());
	    System.out.println("Set TotalPrice: " + review.getProductId());
	    System.out.println("Set TotalPrice: " + review.getUserId());
	    System.out.println("Set TotalPrice: " + review.getContent());
	    
	    System.out.println("여기까지잘됨64");
	    reviewRepository.save(review);
	}
	/*
	public List<AdminReview> searchAndSortReviews(String criteria, Long value, String sortOption) {
	    List<AdminReview> reviews;

	    // 검색 처리
	    if ("productId".equals(criteria)) {
	        reviews = reviewRepository.findByProductId(value);
	    } else if ("userId".equals(criteria)) {
	        reviews = reviewRepository.findByUserId(value);
	    } else {
	        reviews = reviewRepository.findAll(); // 기본 전체 검색
	    }

	    // 정렬 처리
	    switch (sortOption) {
	        case "latest":
	            reviews.sort(Comparator.comparing(AdminReview::getCreatedAt).reversed());
	            break;
	        case "oldest":
	            reviews.sort(Comparator.comparing(AdminReview::getCreatedAt));
	            break;
	        case "userIdAsc":
	            reviews.sort(Comparator.comparing(AdminReview::getUserId));
	            break;
	        case "userIdDesc":
	            reviews.sort(Comparator.comparing(AdminReview::getUserId).reversed());
	            break;
	        default:
	            break; // 기본 정렬 유지
	    }

	    return reviews;
	}
*/
	public List<AdminReview> searchReviews(String criteria, Long value) {
	    List<AdminReview> reviews;

	    // 검색 처리
	    if ("productId".equals(criteria)) {
	        reviews = reviewRepository.findByProductId(value);
	    } else if ("userId".equals(criteria)) {
	        reviews = reviewRepository.findByUserId(value);
	    } else {
	        reviews = reviewRepository.findAll(); // 기본 전체 검색
	    }

	    return reviews;
	}

	public List<AdminReview> sortReviews(List<AdminReview> reviews, String sortOption) {
	    // 정렬 처리
	    switch (sortOption) {
	        case "latest":
	            reviews.sort(Comparator.comparing(AdminReview::getCreatedAt).reversed());
	            break;
	        case "oldest":
	            reviews.sort(Comparator.comparing(AdminReview::getCreatedAt));
	            break;
	        case "userIdAsc":
	            reviews.sort(Comparator.comparing(AdminReview::getUserId));
	            break;
	        case "userIdDesc":
	            reviews.sort(Comparator.comparing(AdminReview::getUserId).reversed());
	            break;
	        default:
	            break; // 기본 정렬 유지
	    }

	    return reviews;
	}
	public List<AdminReview> searchAndSortReviews(String criteria, Long value, String sortOption) {
        List<AdminReview> reviews = searchReviews(criteria, value);
        return sortReviews(reviews, sortOption);
    }
}