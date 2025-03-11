package com.trendy.admin.product;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trendy.product.Product;
import com.trendy.product.ProductDTO;
import com.trendy.product.ProductRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("unused")
public class AdminProductService {
	@Autowired
	private HttpSession session;
	@Autowired
	private ProductRepository productRepository;
	//@Autowired
    //private AdminOrderItemRepository orderItemRepository;

	// [목록 페이지]
	public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductDTO(
                		product.getId(),
                        product.getBrand() != null ? product.getBrand().name() : null,
                        product.getName(),
                        product.getPrice(),
                        product.getImageUrl(),
                        product.getImageDetailUrl1(),
                        product.getImageDetailUrl2(),
                        product.getImageDetailUrl3(),
                        product.getModelId(),
                        product.getColor(),
                        product.getGender() != null ? product.getGender().name() : null,
                        product.getLikeCount(),
                        product.getCreatedBy() != null ? product.getCreatedBy().name() : null
                ))
                .collect(Collectors.toList());
    }
}