package com.trendy.admin.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trendy.product.ProductDTO;
import com.trendy.product.ProductRepository;
import com.trendy.product.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/admin/products")
@CrossOrigin(origins = "https://api.trendy.kg.kr")
public class AdminProductController {
	@Autowired
	private HttpSession session;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getAllProducts(@RequestHeader(value = "Authorization", required = false) String token) {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
