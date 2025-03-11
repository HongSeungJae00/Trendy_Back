package com.trendy.admin.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trendy.order.Order;
import com.trendy.order.OrderDTO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {
	@Autowired
	private HttpSession session;
   @Autowired
   private AdminOrderService orderService;

   // [목록 페이지]
   @GetMapping
   public String getOrderPage(Model model) {
       List<Order> orders = orderService.getAllOrders();

       if (orders == null || orders.isEmpty()) {
           System.out.println("주문 목록이 비어 있습니다."); // 디버깅 로그
       }
       
       model.addAttribute("orders", orders);
       return "AdOrderPage"; // 목록 페이지 템플릿
   }

   // [등록 페이지 이동]
   @GetMapping("/register")
   public String getRegisterPage(Model model) {
       model.addAttribute("order", new OrderDTO());
       return "AdOrderRegister"; // 등록 페이지 템플릿
   }

   // [등록 처리]
   @PostMapping("/register")
   public String registerOrder(@ModelAttribute OrderDTO orderDTO, RedirectAttributes ra) {
       try {
           orderService.registerOrder(orderDTO);
           ra.addFlashAttribute("msg", "주문이 성공적으로 등록되었습니다.");
       } catch (Exception e) {
           ra.addFlashAttribute("msg", "주문 등록 중 오류가 발생했습니다.");
       }
       return "redirect:/admin/order";
   }

   // [수정 페이지 이동]
   @GetMapping("/modify")
   public String getModifyPage(@RequestParam("orderId") Long orderId, Model model) {
       Order order = orderService.findById(orderId);
       if (order == null) {
           return "redirect:/admin/order"; // 주문이 없을 경우 목록으로 리다이렉트
       }
       model.addAttribute("order", order);
       return "AdOrderModify"; // 수정 페이지 템플릿
   }

   // [수정 처리]
   @PostMapping("/modify")
   public String updateOrder(@ModelAttribute OrderDTO orderDTO, RedirectAttributes ra) {
   	System.out.println("여기까지잘됨1");
   	try {
   		System.out.println("여기까지잘됨2");
           String msg = orderService.updateOrder(orderDTO);
           ra.addFlashAttribute("msg", msg);
       } catch (Exception e) {
           ra.addFlashAttribute("msg", "주문 수정 중 오류가 발생했습니다.");
       }
       return "redirect:/admin/order"; // 수정 후 목록 페이지로 리다이렉트
   }
   
   // [삭제]
   @PostMapping("/delete")
   public String deleteOrder(@RequestParam("orderId") Long orderId, RedirectAttributes ra) {
       try {
           orderService.deleteOrder(orderId);
           ra.addFlashAttribute("msg", "주문이 삭제되었습니다.");
       } catch (Exception e) {
           ra.addFlashAttribute("msg", "주문 삭제 중 오류가 발생했습니다.");
           e.printStackTrace();
       }
       return "redirect:/admin/order";
   }

   
   // [선택 삭제]
   @PostMapping("/delete-multiple")
   public String deleteMultipleOrders(@RequestParam(value = "orderIds", required = false) List<Long> orderIds, RedirectAttributes ra) {
       if (orderIds == null || orderIds.isEmpty()) {
           ra.addFlashAttribute("msg", "삭제할 주문을 선택하세요.");
           return "redirect:/admin/order";
       }

       try {
           orderService.deleteMultipleOrders(orderIds);
           ra.addFlashAttribute("msg", "선택된 주문이 성공적으로 삭제되었습니다.");
       } catch (Exception e) {
           ra.addFlashAttribute("msg", "주문 삭제 중 오류가 발생했습니다.");
           e.printStackTrace();
       }
       
       return "redirect:/admin/order";
   }
}
