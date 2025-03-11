package com.trendy.product;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.StandardCopyOption;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final LikeService likeService;
    private final LikeRepository likeRepository;
    private final ProductOptionRepository productOptionRepository;
    // private final SalesDataRepository salesDataRepository;
    
    
    
    
    
    // product entity 생성
    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        Product product = Product.builder()
            .name(productDTO.getName())
            .modelId(productDTO.getModelId())
            .price(productDTO.getPrice())
            .imageUrl(productDTO.getImageUrl())
            .imageDetailUrl1(productDTO.getImageDetailUrl1())
            .imageDetailUrl2(productDTO.getImageDetailUrl2())
            .imageDetailUrl3(productDTO.getImageDetailUrl3())
            .color(productDTO.getColor())
            .warrantyGrade(productDTO.getWarrantyGrade() != null ? 
                Product.WarrantyGrade.valueOf(productDTO.getWarrantyGrade()) : null)
            .region(productDTO.getRegion() != null ? 
                Product.Region.valueOf(productDTO.getRegion()) : null)
            .brand(Product.Brand.valueOf(productDTO.getBrand()))
            .gender(productDTO.getGender() != null ? 
                Product.Gender.valueOf(productDTO.getGender()) : null)
            .createdBy(productDTO.getCreatedBy() != null ? 
                Product.CreatedBy.valueOf(productDTO.getCreatedBy()) : null)
            .build();

        return productRepository.save(product);
    }
    
    
    
    
    
    // 필터링
    public List<ProductDTO> filterProducts(Map<String, List<String>> filters) {
        List<Product> products = productRepository.findAll();

        return products.stream()
            .filter(product -> {
                boolean matches = true;

                // 브랜드 필터
                if (filters.containsKey("brand")) {
                    matches &= filters.get("brand").stream()
                        .anyMatch(filterValue -> product.getBrand() != null &&
                            product.getBrand().name().equalsIgnoreCase(filterValue.trim()));
                }

                // 색상 필터
                if (filters.containsKey("color")) {
                    matches &= filters.get("color").stream()
                        .anyMatch(filterValue -> product.getColor() != null &&
                            product.getColor().equalsIgnoreCase(filterValue.trim()));
                }

                // 사이즈 필터 - ProductOption에서 확인
                if (filters.containsKey("size")) {
                    matches &= product.getProductOptions().stream()
                        .anyMatch(option -> filters.get("size").stream()
                            .anyMatch(filterValue -> option.getSize() != null &&
                                option.getSize().getValue().equals(filterValue.trim()) &&
                                option.isAvailable() &&
                                option.getStockQuantity() > 0));
                }

                // 가격 필터
                if (filters.containsKey("price")) {
                    String priceRange = filters.get("price").get(0);
                    int price = product.getPrice();
                    
                    matches &= switch (priceRange) {
                        case "10만원 이하" -> price <= 100000;
                        case "10만원 - 20만원" -> price > 100000 && price <= 200000;
                        case "20만원 - 30만원" -> price > 200000 && price <= 300000;
                        case "30만원 이상" -> price > 300000;
                        default -> true;
                    };
                }

                // // 성별 필터
                // if (filters.containsKey("gender")) {
                //     matches &= filters.get("gender").stream()
                //         .anyMatch(filterValue -> product.getGender() != null &&
                //             product.getGender().name().equalsIgnoreCase(filterValue.trim()));
                // }

                // // 생성자 필터
                // if (filters.containsKey("created_by")) {
                //     matches &= filters.get("created_by").stream()
                //         .anyMatch(filterValue -> product.getCreatedBy() != null &&
                //             product.getCreatedBy().name().equalsIgnoreCase(filterValue.trim()));
                // }

                return matches;
            })
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



    // 상품 조회
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
    
    
    
    
    // //상품페이지 
    // public List<ProductDTO> getBasicProductDetails() {
    //     List<Product> products = productRepository.findAll(); // DB에서 모든 상품 가져오기

    //     // 필요한 필드만 매핑하여 DTO 생성
    //     return products.stream()
    //             .map(product -> new ProductDTO(
    //             	product.getId(),
    //                 product.getBrand() != null ? product.getBrand().name() : null, // 브랜드
    //                 product.getName(), // 상품명
    //                 product.getPrice(), // 가격
    //                 product.getLikeCount()
    //             ))
    //             .collect(Collectors.toList());
    // }

    //상품 상세
    public ProductDTO getProductDtoById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + id));
        
        // ProductOption에서 사용 가능한 사이즈 목록 가져오기
        List<String> availableSizes = product.getProductOptions().stream()
                .filter(ProductOption::isAvailable)
                .filter(option -> option.getStockQuantity() > 0)
                .map(option -> option.getSize().getValue())
                .collect(Collectors.toList());

        return ProductDTO.builder()
            .id(product.getId())
            .brand(product.getBrand() != null ? product.getBrand().name() : null)
            .name(product.getName())
            .price(product.getPrice())
            .imageUrl(product.getImageUrl())
            .imageDetailUrl1(product.getImageDetailUrl1())
            .imageDetailUrl2(product.getImageDetailUrl2())
            .imageDetailUrl3(product.getImageDetailUrl3())
            .modelId(product.getModelId())
            .color(product.getColor())
            .gender(product.getGender() != null ? product.getGender().name() : null)
            .likeCount(product.getLikeCount())
            .createdBy(product.getCreatedBy() != null ? product.getCreatedBy().name() : null)
            .availableSizes(availableSizes)  // 사용 가능한 사이즈 목록 추가
            .build();
    }


    // 좋아요 상태 토글
    public Map<String, Object> toggleLike(Long userId, Long productId) {
        return likeService.toggleLike(userId, productId);
    }
    
    
    
// 검색
public List<ProductDTO> searchProductsByName(String name) {
    List<Product> products = productRepository.findByNameContaining(name);
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


    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    public int getLikeCount(Long productId) {
        return productRepository.getLikeCount(productId);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + productId));
        
        try {
            // 연관된 좋아요 데이터가 있다면 먼저 삭제
            likeRepository.deleteByProductId(productId);
            
            // 상품 삭제 (ProductOption은 CascadeType.ALL로 설정되어 있어 자동으로 삭제됨)
            productRepository.delete(product);
        } catch (Exception e) {
            throw new RuntimeException("상품 삭제 중 오류가 발생했습니다.", e);
        }
    }



    // 특정 상품의 특정 사이즈 재고 확인
    public boolean checkStockAvailability(Long productId, String size, int requestedQuantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        return product.getProductOptions().stream()
                .filter(option -> option.getSize().getValue().equals(size))
                .filter(ProductOption::isAvailable)
                .anyMatch(option -> option.getStockQuantity() >= requestedQuantity);
    }

    // 상품의 옵션 목록 조회
    public List<ProductOption> getProductOptions(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        return product.getProductOptions();
    }

    // 상품의 특정 사이즈 옵션 ID 조회
    public Long getProductOptionId(Long productId, String size) {
        List<ProductOption> options = getProductOptions(productId);
        return options.stream()
            .filter(option -> option.getSize().getValue().equals(size))
            .findFirst()
            .map(ProductOption::getId)
            .orElseThrow(() -> new IllegalArgumentException("해당 사이즈의 상품이 없습니다."));
    }

    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public ProductOption getProductOption(Long optionId) {
        return productOptionRepository.findById(optionId)
            .orElseThrow(() -> new IllegalArgumentException("상품 옵션을 찾을 수 없습니다."));
    }

    public boolean checkStockAvailability(Long optionId, int quantity) {
        ProductOption option = getProductOption(optionId);
        return option.getStockQuantity() >= quantity;
    }


    @Transactional
    public ProductOption saveProductOption(ProductOption option) {
    return productOptionRepository.save(option);
    }

    @Value("${upload.path}")
    private String uploadPath;

    public String saveImage(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path uploadDir = Paths.get(uploadPath);
        
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path filePath = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + fileName; // 클라이언트에서 접근 가능한 URL 경로 반환
    }


}
