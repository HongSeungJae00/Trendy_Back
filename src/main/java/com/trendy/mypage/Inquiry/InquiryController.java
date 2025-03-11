package com.trendy.mypage.Inquiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trendy.product.ProductRepository;

@Controller("api")
public class InquiryController {

    @Autowired
    private InquiryRepository inquiryRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/inquiries")
    public String showInquiryForm() {
        return "/inquiries";
    }
    
    @GetMapping("/list")
    public String showlist() {
        return "/list";
    }
    
    @PostMapping("/inquiries")
    public String submitInquiry(@ModelAttribute Inquiry inquiry, Model model) {
        if (inquiry.getProductId() == null || !productRepository.existsById(inquiry.getProductId())) {
            inquiry.setProductId(null); // product_id가 없는 경우 null로 처리
        }
        inquiryRepository.save(inquiry);
        model.addAttribute("message", "문의가 성공적으로 등록되었습니다.");
        return "inquiry_success"; // 성공 페이지로 이동
    }

//    @PostMapping("/inquiries/new")
//    public String submitInquiry(
//            @RequestParam("user_id") Long userId,
//            @RequestParam(value = "product_id", required = false) Long productId,
//            @RequestParam("category") String category,
//            @RequestParam("title") String title,
//            @RequestParam("content") String content,
//            Model model
//    ) {
//        Inquiry inquiry = new Inquiry();
//        inquiry.setUserId(userId);
//        inquiry.setProductId(productId);
//        inquiry.setCategory(category);
//        inquiry.setContent(content);
//        inquiryRepository.save(inquiry);
//
//        model.addAttribute("message", "문의사항이 성공적으로 등록되었습니다.");
//        return "/inquiry_success";
//    }
}
