package com.trendy.mypage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trendy.login.User;
import com.trendy.login.UserRepository;
import com.trendy.order.OrderService;
import com.trendy.order.SaleService;

@Controller
@RequestMapping("/mypage/details") // 기본 경로 변경
public class MypageController {

    @GetMapping
    public String myPage(Model model) {
        model.addAttribute("username", "OAuth2 User");
        return "mypageDetails"; // mypageDetails.html
    }


    // 	 @Autowired
	//     private UserRepository userRepository;

	//     @Autowired
	//     private AddressRepository addressRepository;

	//     @Autowired
	//     private OrderService orderService; // OrderService 주입

	//     @Autowired
	//     private SaleService salesService; // SalesService 주입

    // @GetMapping("/mypage")
    // public String myPage(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
    //     if (oAuth2User == null) {
    //         return "redirect:/login";
    //     }

    //     // OAuth2에서 사용자 이메일 가져오기
    //     String email = oAuth2User.getAttributes().get("email").toString();
    //     User user = userRepository.findByEmail(email); // 레포지토리를 인스턴스로 호출

    //     if (user == null) {
    //         return "redirect:/login";
    //     }

    //     // 사용자 ID로 주소 목록 조회
    //     List<Address> addresses = addressRepository.findByUser_UserId(user.getUserId());

    //     model.addAttribute("isAuthenticated", true);
    //     model.addAttribute("userName", user.getUsername());
    //     model.addAttribute("userAddress", addresses);

    //     return "/mypage";
    // }

    // @GetMapping("/addressbook")
    // public String addressBookPage() {
    //     return "addressbook"; // addressbook.html
    // }
    
 
    // @GetMapping("/myloginInformation")
    // public String myloginInformation(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
    //     if (oAuth2User == null) {
    //         // 인증되지 않은 경우 로그인 페이지로 리다이렉트
    //         return "redirect:/login";
    //     }

    //     // OAuth2 사용자 속성 추가
    //     Map<String, Object> attributes = oAuth2User.getAttributes();
        
    //     System.out.println("OAuth2User Attributes: " + oAuth2User.getAttributes());

    //     model.addAttribute("user", attributes);

    //     return "/myloginInformation";
    // }
    
    
    // @GetMapping("/myInformation")
    // public String myInformation(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
    //     if (oAuth2User == null) {
    //         // 인증되지 않은 경우 로그인 페이지로 리다이렉트
    //         return "redirect:/login";
    //     }

    //     // OAuth2 사용자 속성 추가
    //     Map<String, Object> attributes = oAuth2User.getAttributes();
        
    //     System.out.println("OAuth2User Attributes: " + oAuth2User.getAttributes());

    //     model.addAttribute("user", attributes);

    //     return "/myInformation";
    // }
    
    // @GetMapping("/cashReceipt")
    // public String CashReceipt() {
    // 	return "/cashReceipt";
    // }
    // @GetMapping("/sellAccount")
    // public String sellAccount() {
    // 	return "/sellAccount";
    // }

   
    // @GetMapping("/board")
    // public String getBoard(@RequestParam(name = "category", required = false) String category) {
    //     if ("location".equals(category)) {
    //         return "/board-location"; // board-location.html 파일을 반환
    //     }
    //     return "/board"; // 기본 board.html 반환
    // }
    
    // @GetMapping("/withdraw")
    // public String withdraw() {
    // 	return "/withdraw";
    // }
    
    // @GetMapping("/privacy")
    // public String privacy() {
    // 	return "/privacy";
    // }
    
    // @GetMapping("/status")
    // public Map<String, Integer> getStatusCounts(@AuthenticationPrincipal OAuth2User oAuth2User) {
    //     if (oAuth2User == null) {
    //         throw new IllegalStateException("사용자가 인증되지 않았습니다.");
    //     }

    //     // OAuth2에서 사용자 이메일 가져오기
    //     String email = oAuth2User.getAttribute("email");
    //     User currentUser = userRepository.findByEmail(email);

    //     if (currentUser == null) {
    //         throw new IllegalStateException("사용자를 찾을 수 없습니다.");
    //     }

    //     // 서비스 메서드 호출
    //     Map<String, Integer> counts = new HashMap<>();
    //     counts.put("inProgress", orderService.countOrdersByStatus(currentUser.getUserId(), "shipping"));
    //     counts.put("bidding", orderService.countOrdersByStatus(currentUser.getUserId(), "in_progress"));
    //     counts.put("onSale", salesService.countSalesByStatus(currentUser.getUserId(), "on_sale"));
    //     counts.put("completed", salesService.countSalesByStatus(currentUser.getUserId(), "completed"));
    //     return counts;
    // }
    
    // @PostMapping("/api/mypage/update")
    // @ResponseBody
    // public ResponseEntity<String> updateUserInfo(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestBody Map<String, String> updates) {
    //     if (oAuth2User == null) {
    //         return ResponseEntity.status(403).body("사용자가 인증되지 않았습니다.");
    //     }

    //     // OAuth2에서 사용자 이메일 가져오기
    //     String email = oAuth2User.getAttribute("email");
    //     User currentUser = userRepository.findByEmail(email);

    //     if (currentUser == null) {
    //         return ResponseEntity.status(404).body("사용자를 찾을 수 없습니다.");
    //     }

    //     // 업데이트할 필드 처리
    //     updates.forEach((field, value) -> {
    //         switch (field) {
    //             case "password":
    //                 currentUser.setPassword(value);
    //                 break;
    //             case "phone_number":
    //                 currentUser.setPhone_number(value);
    //                 break;
    //             case "user_shoe_size":
    //                 currentUser.setUser_shoe_size(value);
    //                 break;
    //             default:
    //                 throw new IllegalArgumentException("유효하지 않은 필드입니다.");
    //         }
    //     });

    //     userRepository.save(currentUser); // 데이터베이스 업데이트
    //     return ResponseEntity.ok("정보가 성공적으로 업데이트되었습니다.");
    // }
    
    // @PostMapping("/api/user/update-advertising-preferences")
    // @ResponseBody
    // public ResponseEntity<String> updateAdvertisingPreferences(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestBody Map<String, Boolean> preferences) {
    //     if (oAuth2User == null) {
    //         return ResponseEntity.status(403).body("사용자가 인증되지 않았습니다.");
    //     }

    //     // OAuth2에서 사용자 이메일 가져오기
    //     String email = oAuth2User.getAttribute("email");
    //     User currentUser = userRepository.findByEmail(email);

    //     if (currentUser == null) {
    //         return ResponseEntity.status(404).body("사용자를 찾을 수 없습니다.");
    //     }

    //     // 광고성 정보 수신 설정 업데이트
    //     if (preferences.containsKey("message")) {
    //         currentUser.setMessage(preferences.get("message"));
    //     }
    //     if (preferences.containsKey("email_spam")) {
    //         currentUser.setEmail_spam(preferences.get("email_spam"));
    //     }

    //     userRepository.save(currentUser); // 데이터베이스 업데이트
    //     return ResponseEntity.ok("광고성 정보 수신 설정이 성공적으로 업데이트되었습니다.");
    // }
    // @PostMapping("/api/user/withdraw")
    // @ResponseBody
    // public ResponseEntity<String> withdrawUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
    //     if (oAuth2User == null) {
    //         return ResponseEntity.status(403).body("사용자가 인증되지 않았습니다.");
    //     }

    //     // OAuth2에서 사용자 이메일 가져오기
    //     String email = oAuth2User.getAttribute("email");
    //     User currentUser = userRepository.findByEmail(email);

    //     if (currentUser == null) {
    //         return ResponseEntity.status(404).body("사용자를 찾을 수 없습니다.");
    //     }

    //     // 회원 정보 삭제
    //     userRepository.delete(currentUser);
    //     return ResponseEntity.ok("회원탈퇴가 완료되었습니다.");
    // }

    // @PostMapping("/api/user/update-account")
    // @ResponseBody
    // public ResponseEntity<String> updateAccountDetails(
    //     @AuthenticationPrincipal OAuth2User oAuth2User,
    //     @RequestBody Map<String, String> accountDetails
    // ) {
    //     if (oAuth2User == null) {
    //         return ResponseEntity.status(403).body("사용자가 인증되지 않았습니다.");
    //     }

    //     // OAuth2에서 사용자 이메일 가져오기
    //     String email = oAuth2User.getAttribute("email");
    //     User currentUser = userRepository.findByEmail(email);

    //     if (currentUser == null) {
    //         return ResponseEntity.status(404).body("사용자를 찾을 수 없습니다.");
    //     }

    //     // 계좌 정보 업데이트
    //     currentUser.setBank_name(accountDetails.get("bank_name"));
    //     currentUser.setAccount_number(accountDetails.get("account_number"));
    //     currentUser.setAccount_holder(accountDetails.get("account_holder"));

    //     userRepository.save(currentUser); // 변경 사항 저장

    //     return ResponseEntity.ok("계좌 정보가 성공적으로 업데이트되었습니다.");
    // }
    
    // @GetMapping("/QandA")
    // public String showQandAPage() {
    //     return "/QandA"; // QandA.html과 매칭
    // }
}