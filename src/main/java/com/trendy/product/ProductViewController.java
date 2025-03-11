package com.trendy.product;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.trendy.login.CustomUserDetails;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductViewController {

    private final ProductService productService;



    // 필터 페이지를 반환하는 메서드
    @GetMapping("/filter")
    public String showFilterPage() {
        return "filter-buttons"; // templates/filter-buttons.html 반환
    }

    // 상품 리스트를 반환하는 메서드
    @GetMapping("/product-list")
    public String showProductList(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        List<ProductDTO> products = productService.getAllProducts();
        model.addAttribute("products", products);
        
        // 로그인 여부와 관계없이 상품 목록 표시
        if (userDetails != null) {
            model.addAttribute("userId", userDetails.getUserId());
        } else {
            model.addAttribute("userId", null);
        }
        return "product-list";
    }

    // 상품 상세 페이지 - 인증 없이 접근 가능하도록 수정
    @GetMapping("/products/{id}")
    public String getProductDetail(@PathVariable("id") Long id, 
        Model model, 
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        // 상품 정보 조회
        ProductDTO product = productService.getProductDtoById(id);
        if (product == null) {
            return "error/404";
        }
        
        // 상품 정보는 항상 모델에 추가
        model.addAttribute("product", product);
        
        // 로그인한 사용자의 정보가 있다면 추가
        if (userDetails != null) {
            model.addAttribute("userId", userDetails.getUserId());
        } else {
            model.addAttribute("userId", null);
        }
        
        return "product-detail";
    }
}

