package com.trendy.admin.quality;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("unused")
public class AdminQualityService {
	@Autowired
	private HttpSession session;
	@Autowired
	private AdminQualityRepository qualityRepository;
	//@Autowired
    //private AdminOrderItemRepository orderItemRepository;

	// [목록 페이지]
	public List<AdminQuality> getAllQualitys() {
	    List<AdminQuality> qualitys = qualityRepository.findAllByOrderByProductIdDesc();
	    qualitys.forEach(quality -> {
	        System.out.println("Product ID: " + quality.getProductId());
	    });
	    return qualitys;
	}

	public List<AdminQuality> searchByProductId(Long productId) {
	    return qualityRepository.findByProductIdAsList(productId);
	}

	    public List<String> getDistinctColors() {
	        return qualityRepository.findDistinctColors();
	    }
	    public List<String> getDistinctSizes() {
	        return qualityRepository.findDistinctSizes();
	    }
	    public List<String> getDistinctIsArriveds() {
	        return qualityRepository.findDistinctIsArriveds();
	    }
	    public List<String> getDistinctStates() {
	        return qualityRepository.findDistinctStates();
	    }
	    public List<String> getDistinctWarrantyGrades() {
	        return qualityRepository.findDistinctWarrantyGrades();
	    }
	    public List<String> getDistinctCreatedBys() {
	        return qualityRepository.findDistinctCreatedBys();
	    }
	    
	// 주문 상세 정보 조회
    public AdminQuality findByProductId(Long productId) {
        return qualityRepository.findByProductId(productId).orElse(null);
    }

	// [수정 페이지]
	// 게시물 조회
	public AdminQuality findById(Long productId) {
		return qualityRepository.findByProductId(productId).orElse(null);
	}
	/*
	// 게시물 수정 처리
	@Transactional
	public String updateQuality(AdminQualityDTO qualityDTO) {
		System.out.println("여기까지잘됨3");
	    AdminQuality quality = qualityRepository.findByProductId(qualityDTO.getProductId()).orElse(null);
	    if (quality == null) {
	        return "주문이 존재하지 않습니다.";
	    }
	    quality.setProductName(qualityDTO.getProductName());
	    quality.setCreatedBy(AdminQuality.CreatedBy.valueOf(qualityDTO.getCreatedBy()));
	    quality.setColor(qualityDTO.getColor());
	    quality.setSize(qualityDTO.getSize());
	    quality.setIsArrived(AdminQuality.IsArrived.valueOf(qualityDTO.getIsArrived()));
	    quality.setCheckPerson(qualityDTO.getCheckPerson());
	    quality.setState(AdminQuality.State.valueOf(qualityDTO.getState()));
	    quality.setWarrantyGrade(AdminQuality.WarrantyGrade.valueOf(qualityDTO.getWarrantyGrade()));
	    try {
	        qualityRepository.save(quality); // 저장 시점
	        System.out.println("Quality saved: " + quality); // 저장 확인 로그
	    } catch (Exception e) {
	        System.out.println("DB 저장 실패: " + e.getMessage());
	        e.printStackTrace();
	        return "DB 저장 중 오류 발생";
	    }
	    return "성공적으로 수정되었습니다.";
	}
*/
	@Transactional
    public String updateQuality(AdminQualityDTO qualityDTO) {
        System.out.println("Quality 업데이트 시작");
        
        AdminQuality quality = qualityRepository.findByProductId(qualityDTO.getProductId())
            .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

        try {
            // Quality 테이블 업데이트
            quality.setProductName(qualityDTO.getProductName());
            quality.setCreatedBy(AdminQuality.CreatedBy.valueOf(qualityDTO.getCreatedBy()));
            quality.setColor(qualityDTO.getColor());
            quality.setSize(qualityDTO.getSize());
            quality.setIsArrived(AdminQuality.IsArrived.valueOf(qualityDTO.getIsArrived()));
            quality.setCheckPerson(qualityDTO.getCheckPerson());
            quality.setState(AdminQuality.State.valueOf(qualityDTO.getState()));
            quality.setWarrantyGrade(AdminQuality.WarrantyGrade.valueOf(qualityDTO.getWarrantyGrade()));

            qualityRepository.save(quality);
            System.out.println("Quality saved: " + quality);
            
            return "성공적으로 수정되었습니다.";
            
        } catch (EntityNotFoundException e) {
            System.out.println("상품을 찾을 수 없음: " + e.getMessage());
            return e.getMessage();
        } catch (Exception e) {
            System.out.println("DB 저장 실패: " + e.getMessage());
            e.printStackTrace();
            return "DB 저장 중 오류 발생";
        }
    }
	// 게시물 삭제
	@Transactional
    public void deleteQuality(Long productId) {
        try {
        	qualityRepository.deleteByProductId(productId);
        } catch (Exception e) {
            throw new RuntimeException("삭제 중 오류 발생: " + e.getMessage());
        }
    }
	
	
	// 다중 게시물 삭제
	@Transactional
    public void deleteMultipleQualitys(List<Long> productIds) {
        try {
        	qualityRepository.deleteAllByProductIdIn(productIds);
        } catch (Exception e) {
            throw new RuntimeException("다중 삭제 중 오류 발생: " + e.getMessage());
        }
    }
/*
	public List<AdminProduct> searchAndSortProducts(String brand, String value, String sortOption) {
	    List<AdminProduct> products;

	    // 검색 처리
	    if (value != null && !value.isEmpty()) {
	        try {
	            Long productId = Long.parseLong(value);
	            products = productRepository.findByProductIdAsList(productId);
	        } catch (NumberFormatException e) {
	            products = List.of(); // 숫자로 변환할 수 없는 경우 빈 리스트 반환
	        }
	    } else if (brand != null && !brand.isEmpty()) {
	        products = productRepository.findByBrand(AdminProduct.Brand.valueOf(brand));
	    } else {
	        products = productRepository.findAll(); // 기본 전체 검색
	    }
	    return products;
	}
*/
/*
	public List<AdminProduct> searchAndSortProducts(String brand, String value, String sortOption) {
	    List<AdminProduct> products = productRepository.findAll(); // 기본 전체 목록

	    // 검색 조건 처리
	    if (value != null && !value.isEmpty()) {
	        try {
	            Long productId = Long.parseLong(value);
	            products = productRepository.findByProductIdAsList(productId);
	        } catch (NumberFormatException e) {
	            return List.of(); // 잘못된 형식의 경우 빈 리스트 반환
	        }
	    } else if (brand != null && !brand.isEmpty()) {
	        products = productRepository.findByBrand(AdminProduct.Brand.valueOf(brand));
	    }

	    return products;
	}
*/
}