package com.trendy.admin.user;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("unused")
public class AdminUserService {
	@Autowired
	private HttpSession session;
	@Autowired
    private JdbcTemplate jdbcTemplate;
	@Autowired
	private AdminUserRepository userRepository;
	//@Autowired
    //private AdminOrderItemRepository orderItemRepository;

	// [목록 페이지]
	public List<AdminUser> getAllUsers() {
	    List<AdminUser> users = userRepository.findAllByOrderByUserIdDesc();
	    users.forEach(user -> {
	        System.out.println("User ID: " + user.getUserId());
	    });
	    return users;
	}
	
	public AdminUser getUserById(Long userId) {
        return userRepository.findByUserId(userId).orElse(null);
    }
	
	// 주문 상세 정보 조회
    public AdminUser findByUserId(Long userId) {
        return userRepository.findByUserId(userId).orElse(null);
    }
    /*
    // Order_Items 조회
    public List<AdminOrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
	*/
	// [수정 페이지]
	// 게시물 조회
	public AdminUser findById(Long userId) {
		return userRepository.findByUserId(userId).orElse(null);
	}
	// 다중 게시물 삭제
	@Transactional
    public void deleteMultipleUsers(List<Long> userIds) {
        try {
        	userRepository.deleteAllByUserIdIn(userIds);
        } catch (Exception e) {
            throw new RuntimeException("다중 삭제 중 오류 발생: " + e.getMessage());
        }
    }
	public List<AdminUser> searchAndSortUsers(String criteria, Long value, String sortOption) {
	    List<AdminUser> users;

	    // 검색 처리
	    if ("userId".equals(criteria)) {
	        users = userRepository.findByUserId(value).stream().toList();
	    } else {
	        users = userRepository.findAll(); // 기본적으로 전체 유저를 가져옴
	    }
	    List<AdminUser> mutableUsers = new ArrayList<>(users);
	    // 정렬 처리
	    switch (sortOption) {
	        case "latest":
	        	mutableUsers.sort(Comparator.comparing(AdminUser::getCreatedAt).reversed());
	            break;
	        case "oldest":
	        	mutableUsers.sort(Comparator.comparing(AdminUser::getCreatedAt));
	            break;
	        default:
	            break; // 정렬 옵션이 없으면 기본 정렬 유지
	    }

	    return mutableUsers;
	}
	public Map<String, Object> getPurchaseInfo(Long userId) {
        String purchaseQuery = "SELECT COUNT(*) AS totalPurchases, SUM(price) AS totalPurchaseAmount FROM Orders WHERE user_id = ?";
        return jdbcTemplate.queryForMap(purchaseQuery, userId);
    }

    public Map<String, Object> getSalesInfo(Long userId) {
        String salesQuery = "SELECT COUNT(*) AS totalSales, SUM(total_price) AS totalSalesAmount FROM Sales_Data WHERE user_id = ?";
        return jdbcTemplate.queryForMap(salesQuery, userId);
    }
}