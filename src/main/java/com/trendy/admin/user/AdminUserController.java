package com.trendy.admin.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trendy.admin.review.AdminReview;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {
	@Autowired
	private HttpSession session;
    @Autowired
    private AdminUserService userService;

    // [목록 페이지]
    @GetMapping
    public String getUserPage(Model model) {
        List<AdminUser> users = userService.getAllUsers();

        if (users == null || users.isEmpty()) {
            System.out.println("주문 목록이 비어 있습니다."); // 디버깅 로그
        }
        
        model.addAttribute("users", users);
        return "AdUserPage"; // 목록 페이지 템플릿
    }
    @GetMapping("/modify")
    public String getUserProfile(@RequestParam("userId") Long userId, Model model) {
        AdminUser user = userService.getUserById(userId);
        Map<String, Object> purchaseInfo = userService.getPurchaseInfo(userId);
        Map<String, Object> salesInfo = userService.getSalesInfo(userId);

        model.addAttribute("user", user);
        model.addAttribute("purchaseInfo", purchaseInfo);
        model.addAttribute("salesInfo", salesInfo);

        return "AdUserProfile"; // 유저 프로필 페이지
    }
    
    // [선택 삭제]
    @PostMapping("/delete-multiple")
    public String deleteMultipleUsers(@RequestParam(value = "userIds", required = false) List<Long> userIds, RedirectAttributes ra) {
        if (userIds == null || userIds.isEmpty()) {
            ra.addFlashAttribute("msg", "삭제할 주문을 선택하세요.");
            return "redirect:/admin/user";
        }

        try {
        	userService.deleteMultipleUsers(userIds);
            ra.addFlashAttribute("msg", "선택된 주문이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            ra.addFlashAttribute("msg", "주문 삭제 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
        
        return "redirect:/admin/user";
    }
    @GetMapping("/search")
    public String searchAndSortReviews(
        @RequestParam(value = "criteria", required = false) String criteria,
        @RequestParam(value = "value", required = false) Long value,
        @RequestParam(value = "sortOption", defaultValue = "latest") String sortOption,
        Model model
    ) {
    	System.out.println("정렬 옵션: " + sortOption);
        List<AdminUser> users = userService.searchAndSortUsers(criteria, value, sortOption);
        model.addAttribute("users", users);
        return "AdUserPage";
    }
    @GetMapping("/info")
    public String getUserInfo(@RequestParam("userId") Long userId, Model model) {
        // 구매 및 판매 정보 가져오기
        Map<String, Object> purchaseInfo = userService.getPurchaseInfo(userId);
        Map<String, Object> salesInfo = userService.getSalesInfo(userId);

        // 모델에 데이터 추가
        model.addAttribute("purchaseInfo", purchaseInfo);
        model.addAttribute("salesInfo", salesInfo);

        return "userInfoPage"; // 수정된 HTML 파일 이름
    }

}
