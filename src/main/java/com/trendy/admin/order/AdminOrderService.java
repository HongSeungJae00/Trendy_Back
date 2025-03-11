package com.trendy.admin.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trendy.order.Order;
import com.trendy.order.OrderDTO;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("unused")
public class AdminOrderService {
	@Autowired
	private HttpSession session;
	@Autowired
	private AdminOrderRepository orderRepository;
	//@Autowired
   //private AdminOrderItemRepository orderItemRepository;

	// [목록 페이지]
	public List<Order> getAllOrders() {
	    List<Order> orders = orderRepository.findAllByOrderByOrderIdDesc();
	    orders.forEach(order -> {
	        System.out.println("Order ID: " + order.getOrderId());
	    });
	    return orders;
	}
	
	// 주문 상세 정보 조회
   public Order findByOrderId(Long orderId) {
       return orderRepository.findByOrderId(orderId).orElse(null);
   }
   /*
   // Order_Items 조회
   public List<AdminOrderItem> getOrderItemsByOrderId(Long orderId) {
       return orderItemRepository.findByOrderId(orderId);
   }
	*/
	// [수정 페이지]
	// 게시물 조회
	public Order findById(Long orderId) {
		return orderRepository.findByOrderId(orderId).orElse(null);
	}

	// 게시물 수정 처리
	public String updateOrder(OrderDTO orderDTO) {
		System.out.println("여기까지잘됨3");
	    Order order = orderRepository.findByOrderId(orderDTO.getOrderId()).orElse(null);
	    if (order == null) {
	        return "주문이 존재하지 않습니다.";
	    }
	    System.out.println("여기까지잘됨4");
	    order.setUserId(orderDTO.getUserId());
	    System.out.println("여기까지잘됨44");
	    order.setProductId(orderDTO.getProductId());
	    System.out.println("여기까지잘됨55");
	    order.setPrice(orderDTO.getPrice());
	    System.out.println("여기까지잘됨6");
	    System.out.println("Received status: '" + orderDTO.getStatus() + "'");
	    Order.OrderStatus status = orderDTO.getStatus();
	    System.out.println("여기까지잘됨5");
	    order.setStatus(status);
	    orderRepository.save(order);
	    System.out.println("여기까지잘됨5");
	    return "주문이 성공적으로 수정되었습니다.";
	}


	// 게시물 삭제
	@Transactional
   public void deleteOrder(Long orderId) {
       try {
           orderRepository.deleteById(orderId);
       } catch (Exception e) {
           throw new RuntimeException("삭제 중 오류 발생: " + e.getMessage());
       }
   }
	
	
	// 다중 게시물 삭제
	@Transactional
   public void deleteMultipleOrders(List<Long> orderIds) {
       try {
           orderRepository.deleteAllByOrderIdIn(orderIds);
       } catch (Exception e) {
           throw new RuntimeException("다중 삭제 중 오류 발생: " + e.getMessage());
       }
   }
	
	 // 게시물 등록
	public void registerOrder(OrderDTO orderDTO) {
		System.out.println("여기까지잘됨61");
	    Order order = new Order();
	    System.out.println("여기까지잘됨62");
	    order.setUserId(orderDTO.getUserId());
	    System.out.println("여기까지잘됨62");
	    order.setProductId(orderDTO.getProductId());
	    System.out.println("여기까지잘됨63");
	    order.setPrice(orderDTO.getPrice());
	    System.out.println("여기까지잘됨64");
	    Order.OrderStatus status = orderDTO.getStatus();
	    System.out.println("여기까지잘됨5");
	    order.setStatus(status);
	    System.out.println("여기까지잘됨6");
	    System.out.println("Set TotalPrice: " + order.getOrderId());
	    System.out.println("Set TotalPrice: " + order.getCreatedAt());
	    System.out.println("Set TotalPrice: " + order.getProductId());
	    System.out.println("Set TotalPrice: " + order.getStatus());
	    System.out.println("Set TotalPrice: " + order.getPrice());
	    System.out.println("Set TotalPrice: " + order.getUserId());
	    
	    System.out.println("여기까지잘됨64");
	    System.out.println("여기까지잘됨65");
	    orderRepository.save(order);
	}

}