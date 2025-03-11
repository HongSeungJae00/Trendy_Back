package com.trendy.mypage.address;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/address") // 고유한 경로 설정
public class AddressController {
	
	private final AddressService addressService = null;
	
	@PostMapping("/saveAddress")
	public ResponseEntity<Map<String, String>> saveAddress(@RequestBody Map<String, String> requestBody) {
	    Long userId = Long.parseLong(requestBody.get("userId"));
	    String address = requestBody.get("address");

	    try {
	        boolean success = addressService.updateAddress(userId, address);

	        Map<String, String> response = new HashMap<>();
	        response.put("message", "주소 저장 성공");
	        return ResponseEntity.ok(response);

	    } catch (IllegalStateException e) {
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "모든 주소가 이미 저장되어 있습니다.");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	}

}
