package com.trendy.admin.review;

import java.util.Comparator;
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

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/review")
public class AdminReviewController {
	@Autowired
	private HttpSession session;
    @Autowired
    private AdminReviewService reviewService;
    @Autowired
    private AdminReviewRepository reviewRepository;

    // [목록 페이지]
    @GetMapping
    public String getReviewPage(Model model) {
        List<AdminReview> reviews = reviewService.getAllReviews();

        if (reviews == null || reviews.isEmpty()) {
            System.out.println("주문 목록이 비어 있습니다."); // 디버깅 로그
        }
        
        model.addAttribute("reviews", reviews);
        return "AdReviewPage"; // 목록 페이지 템플릿
    }

    // [등록 페이지 이동]
    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("review", new AdminReviewDTO());
        return "AdReviewRegister"; // 등록 페이지 템플릿
    }

    // [등록 처리]
    @PostMapping("/register")
    public String registerReview(@ModelAttribute AdminReviewDTO reviewDTO, RedirectAttributes ra) {
        try {
            reviewService.registerReview(reviewDTO);
            ra.addFlashAttribute("msg", "주문이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            ra.addFlashAttribute("msg", "주문 등록 중 오류가 발생했습니다.");
        }
        return "redirect:/admin/review";
    }

    // [수정 페이지 이동]
    @GetMapping("/modify")
    public String getModifyPage(@RequestParam("reviewId") Long reviewId, Model model) {
        AdminReview review = reviewService.findByReviewId(reviewId);
        if (review == null) {
            return "redirect:/admin/review"; // 주문이 없을 경우 목록으로 리다이렉트
        }
        model.addAttribute("review", review);
        return "AdReviewModify"; // 수정 페이지 템플릿
    }

    // [수정 처리]
    @PostMapping("/modify")
    public String updateReview(@ModelAttribute AdminReviewDTO reviewDTO, RedirectAttributes ra) {
    	System.out.println("여기까지잘됨1");
    	try {
    		System.out.println("여기까지잘됨2");
            String msg = reviewService.updateReview(reviewDTO);
            ra.addFlashAttribute("msg", msg);
        } catch (Exception e) {
            ra.addFlashAttribute("msg", "주문 수정 중 오류가 발생했습니다.");
        }
        return "redirect:/admin/review"; // 수정 후 목록 페이지로 리다이렉트
    }
    
    // [삭제]
    @PostMapping("/delete")
    public String deleteReview(@RequestParam("reviewId") Long reviewId, RedirectAttributes ra) {
        try {
            reviewService.deleteReview(reviewId);
            ra.addFlashAttribute("msg", "주문이 삭제되었습니다.");
        } catch (Exception e) {
            ra.addFlashAttribute("msg", "주문 삭제 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
        return "redirect:/admin/review";
    }

    
    // [선택 삭제]
    @PostMapping("/delete-multiple")
    public String deleteMultipleReviews(@RequestParam(value = "reviewIds", required = false) List<Long> reviewIds, RedirectAttributes ra) {
        if (reviewIds == null || reviewIds.isEmpty()) {
            ra.addFlashAttribute("msg", "삭제할 주문을 선택하세요.");
            return "redirect:/admin/review";
        }

        try {
            reviewService.deleteMultipleReviews(reviewIds);
            ra.addFlashAttribute("msg", "선택된 주문이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            ra.addFlashAttribute("msg", "주문 삭제 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
        
        return "redirect:/admin/review";
    }
    /*
    @GetMapping("/search")
    public String searchAndSortReviews(
        @RequestParam(value = "criteria", required = false) String criteria,
        @RequestParam(value = "value", required = false) Long value,
        @RequestParam(value = "sortOption", defaultValue = "latest") String sortOption,
        Model model
    ) {
        List<AdminReview> reviews = reviewService.searchAndSortReviews(criteria, value, sortOption);
        model.addAttribute("reviews", reviews);
        return "AdReviewPage";
    }*/
    @GetMapping("/search")
    public String searchAndSortReviews(
        @RequestParam(value = "criteria", required = false) String criteria,
        @RequestParam(value = "value", required = false) Long value,
        @RequestParam(value = "sortOption", defaultValue = "latest") String sortOption,
        Model model
    ) {
        List<AdminReview> reviews = reviewService.searchAndSortReviews(criteria, value, sortOption);
        model.addAttribute("reviews", reviews);
        return "AdReviewPage";
    }
}
