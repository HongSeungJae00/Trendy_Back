package com.trendy.admin.quality;

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
@RequestMapping("/admin/quality")
public class AdminQualityController {
	@Autowired
	private HttpSession session;
    @Autowired
    private AdminQualityService qualityService;
    @Autowired
    private AdminQualityRepository qualityRepository;

    // [목록 페이지]
    @GetMapping
    public String getQualityPage(Model model) {
        List<AdminQuality> qualitys = qualityService.getAllQualitys();

        if (qualitys == null || qualitys.isEmpty()) {
            System.out.println("주문 목록이 비어 있습니다."); // 디버깅 로그
        }
        
        model.addAttribute("qualitys", qualitys);
        return "AdQualityPage"; // 목록 페이지 템플릿
    }

    // [수정 페이지 이동]
    @GetMapping("/modify")
    public String getModifyPage(@RequestParam("productId") Long productId, Model model) {
        AdminQuality quality = qualityService.findByProductId(productId);
        if (quality == null) {
            return "redirect:/admin/quality"; // 주문이 없을 경우 목록으로 리다이렉트
        }
        model.addAttribute("quality", quality);
        model.addAttribute("createdBys", AdminQuality.CreatedBy.values()); // CreatedBy Enum
        model.addAttribute("colors", qualityService.getDistinctColors());
        model.addAttribute("sizes", qualityService.getDistinctSizes());
        model.addAttribute("isArriveds", AdminQuality.IsArrived.values()); // IsArrived Enum
        model.addAttribute("states", AdminQuality.State.values()); // State Enum
        model.addAttribute("warrantyGrades", AdminQuality.WarrantyGrade.values()); // WarrantyGrade Enum
        return "AdQualityModify";
    }

    @PostMapping("/modify")
    public String updateQuality(@ModelAttribute AdminQualityDTO qualityDTO, RedirectAttributes ra) {
    	System.out.println("여기까지잘됨1");
    	try {
    		System.out.println("여기까지잘됨2");
            String msg = qualityService.updateQuality(qualityDTO);
            ra.addFlashAttribute("msg", msg);
        } catch (Exception e) {
            ra.addFlashAttribute("msg", "주문 수정 중 오류가 발생했습니다.");
        }
        return "redirect:/admin/quality"; // 수정 후 목록 페이지로 리다이렉트
    }

    // [삭제]
    @PostMapping("/delete")
    public String deleteQuality(@RequestParam("productId") Long productId, RedirectAttributes ra) {
        try {
        	qualityService.deleteQuality(productId);
            ra.addFlashAttribute("msg", "주문이 삭제되었습니다.");
        } catch (Exception e) {
            ra.addFlashAttribute("msg", "주문 삭제 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
        return "redirect:/admin/quality";
    }

    
    // [선택 삭제]
    @PostMapping("/delete-multiple")
    public String deleteMultipleQualitys(@RequestParam(value = "productIds", required = false) List<Long> productIds, RedirectAttributes ra) {
        if (productIds == null || productIds.isEmpty()) {
            ra.addFlashAttribute("msg", "삭제할 주문을 선택하세요.");
            return "redirect:/admin/quality";
        }

        try {
        	qualityService.deleteMultipleQualitys(productIds);
            ra.addFlashAttribute("msg", "선택된 주문이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            ra.addFlashAttribute("msg", "주문 삭제 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
        
        return "redirect:/admin/quality";
    }
    /*
    @GetMapping("/search")
    public String searchProducts(
        @RequestParam(value = "value", required = false) String value,
        @RequestParam(value = "brand", required = false) String brand,
        Model model
    ) {
        List<AdminProduct> products;

        // 검색 조건
        if (value != null && !value.isEmpty()) {
            try {
                Long productId = Long.parseLong(value);
                products = productService.searchByProductId(productId);
            } catch (NumberFormatException e) {
                products = List.of(); // 숫자가 아닌 값이 입력된 경우 빈 리스트 반환
            }
        } else if (brand != null && !brand.isEmpty()) {
            products = productService.searchByBrand(brand);
        } else {
            products = productService.getAllProducts(); // 전체 목록 반환
        }

        model.addAttribute("products", products);
        model.addAttribute("brands", productService.getDistinctBrands());
        return "AdProductPage";
    }
    */
    /*
    @GetMapping("/search")
    public String searchProducts(
        @RequestParam(value = "value", required = false) String value,
        @RequestParam(value = "brand", required = false) String brand,
        Model model
    ) {
        List<AdminProduct> products;

        // 검색 조건 처리
        if (value != null && !value.isEmpty()) {
            try {
                Long productId = Long.parseLong(value);
                products = productService.searchByProductId(productId);
            } catch (NumberFormatException e) {
                model.addAttribute("error", "Invalid product ID format.");
                products = List.of(); // 잘못된 형식의 경우 빈 리스트 반환
            }
        } else if (brand != null && !brand.isEmpty()) {
            products = productService.searchByBrand(brand);
        } else {
            products = productService.getAllProducts(); // 전체 목록 반환
        }

        // 브랜드 목록은 항상 제공
        model.addAttribute("brands", productService.getDistinctBrands());
        model.addAttribute("products", products);

        return "AdProductPage";
    }
    */
}
