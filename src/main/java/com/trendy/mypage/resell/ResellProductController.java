package com.trendy.mypage.resell;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trendy.login.CustomUserDetails;
import com.trendy.product.Product;
import com.trendy.product.ProductOption;
import com.trendy.product.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resell")
@Slf4j
public class ResellProductController {
    
    private final ProductService productService;
    private final SalesDataService salesDataService;

    @PostMapping("/products/register")
    public ResponseEntity<?> registerResellProduct(
            @RequestParam("name") String name,
            @RequestParam("modelId") String modelId,
            @RequestParam("price") String price,
            @RequestParam("brand") String brand,
            @RequestParam("gender") String gender,
            @RequestParam("color") String color,
            @RequestParam("size") String size,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "imageDetail1", required = false) MultipartFile imageDetail1,
            @RequestParam(value = "imageDetail2", required = false) MultipartFile imageDetail2,
            @RequestParam(value = "imageDetail3", required = false) MultipartFile imageDetail3,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            if (userDetails == null) {
                return ResponseEntity.badRequest().body("로그인이 필요합니다.");
            }

            // 이미지 저장 처리
            String imageUrl = null;
            String imageDetailUrl1 = null;
            String imageDetailUrl2 = null;
            String imageDetailUrl3 = null;

            if (image != null && !image.isEmpty()) {
                imageUrl = productService.saveImage(image);
            }
            if (imageDetail1 != null && !imageDetail1.isEmpty()) {
                imageDetailUrl1 = productService.saveImage(imageDetail1);
            }
            if (imageDetail2 != null && !imageDetail2.isEmpty()) {
                imageDetailUrl2 = productService.saveImage(imageDetail2);
            }
            if (imageDetail3 != null && !imageDetail3.isEmpty()) {
                imageDetailUrl3 = productService.saveImage(imageDetail3);
            }
    
            // Product 엔티티 생성
            Product product = Product.builder()
                .name(name)
                .modelId(modelId)
                .price(Integer.parseInt(price))
                .imageUrl(imageUrl)
                .imageDetailUrl1(imageDetailUrl1)
                .imageDetailUrl2(imageDetailUrl2)
                .imageDetailUrl3(imageDetailUrl3)
                .color(color)
                .brand(Product.Brand.valueOf(brand))
                .gender(Product.Gender.valueOf(gender))
                .productOptions(new ArrayList<>())
                .build();
    
            // 먼저 Product 저장
            Product savedProduct = productService.saveProduct(product);
    
            // ProductOption 생성 및 저장
            ProductOption option = ProductOption.builder()
                .product(savedProduct)
                .size(ProductOption.Size.fromValue(size))
                .isAvailable(true)
                .stockQuantity(1)
                .build();
    
            ProductOption savedOption = productService.saveProductOption(option);
    
            // SalesData 생성 및 저장
            SalesData salesData = SalesData.builder()
                .modelId(savedProduct.getModelId())
                .productOption(savedOption)
                .userId(userDetails.getUserId())
                .quantity(1)
                .price(savedProduct.getPrice())
                .saleDate(LocalDate.now())
                .build();
            
            log.debug("Creating SalesData with price: {}", salesData.getPrice());
            salesDataService.saveSalesData(salesData);
            
            return ResponseEntity.ok(savedProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteResellProduct(
            @PathVariable Long productId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            if (userDetails == null) {
                return ResponseEntity.badRequest().body("로그인이 필요합니다.");
            }

            // 상품과 관련된 판매 데이터 조회
            SalesData salesData = salesDataService.findByProductOptionProductIdAndUserId(productId, userDetails.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 상품의 판매 데이터를 찾을 수 없습니다."));

            // 판매 데이터 삭제
            salesDataService.deleteSalesData(salesData);

            // 상품 삭제
            productService.deleteProduct(productId);

            return ResponseEntity.ok().body("상품이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
