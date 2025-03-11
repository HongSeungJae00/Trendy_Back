package com.trendy.mypage.CustomerCenter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/CustomerCenter")
public class CustomerCenterController {

    private final NoticeEventRepository noticeRepository; // 변수명 수정

    // 생성자 주입
    public CustomerCenterController(NoticeEventRepository noticeRepository) {
        this.noticeRepository = noticeRepository; // 대소문자 수정
    } 
    
    @GetMapping("/search")
    public String searchNotices(@RequestParam("query") String query, Model model) {
        List<NoticeEvent> filteredNotices = noticeRepository.findByTitleContaining(query);
        model.addAttribute("notices", filteredNotices);
        return "CustomerCenter"; // View 이름
    }

    // 메인 페이지 조회
    @GetMapping
    public String getCustomerCenterPage(Model model) {
        List<NoticeEvent> notices = noticeRepository.findAll(); // 모든 공지 및 이벤트 가져오기
        model.addAttribute("notices", notices);
        return "/CustomerCenter"; // CustomerCenter.html 템플릿 반환
    }

    // 상세 페이지 조회
    @GetMapping("/detail/{id}")
    public String getCustomerCenterDetail(@PathVariable("id") Long id, Model model) {
        NoticeEvent notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다. ID: " + id));
        model.addAttribute("notice", notice);
        return "/CustomerCenterDetail";
    }
    
    @GetMapping("/notice")
    public String getNoticePage(Model model) {
        List<NoticeEvent> notices = noticeRepository.findByType("notice");
        model.addAttribute("notices", notices);
        return "/notice"; // notice.html 반환
    }
    
    @GetMapping("/CustomerCenterDetail")
    public String CustomerCenterDetail() {
        return "/CustomerCenterDetail";
    }

    private Map<String, Object> createSampleQandAData() {
        Map<String, Object> data = new HashMap<>();
        data.put("lastUpdated", "2023년 6월 7일");
        data.put("penaltyInfo", List.of(
                Map.of("title", "판매거부", "details", List.of(
                        "판매 거래 대기 이후, 1시간 이내 판매 거부 - 5.0%",
                        "판매 거래 대기 이후, 1시간 이후 판매 거부 - 10.0%"
                )),
                Map.of("title", "발송지연", "details", List.of(
                        "판매 거래 체결 후, 48시간(일요일·공휴일 제외) 이내 발송 정보 미입력 - 10.0%"
                )),
                Map.of("title", "미입고", "details", List.of(
                        "발송 정보 입력 후, 5일(일요일·공휴일 제외) 이내 검사센터에 미도착 - 10.0%",
                        "가송장 등 허위 정보 입력 - 10.0%",
                        "거래 체결 전 상품 발송 - 10.0%"
                )),
                Map.of("title", "검수기준 위반", "details", List.of(
                        "상품 불일치 - 10.0%",
                        "사이즈 불일치 - 10.0%",
                        "기본 구성품 누락 - 10.0%"
                )),
                Map.of("title", "가품/사용감", "details", List.of(
                        "가품 - 15.0%",
                        "사용감 - 15.0%"
                ))
        ));
        return data;
    }

       
    @GetMapping("/filter")
    public String filterNotices(@RequestParam("type") String type, Model model) {
        List<NoticeEvent> filteredNotices;
        if ("all".equals(type)) {
            filteredNotices = noticeRepository.findAll();
        } else {
            filteredNotices = noticeRepository.findByType(type);
        }
        model.addAttribute("notices", filteredNotices);
        return "CustomerCenter";
    }
    

    private Map<String, Object> createStandardData() {
        Map<String, Object> data = new HashMap<>();
        data.put("updateDate", "2021년 11월 29일");
        data.put("applyDate", "2021년 12월 03일 00:00");
        data.put("content", "첨부된 이미지 내용을 기반으로 상세한 설명 작성.");
        data.put("sections", List.of(
                Map.of("title", "불합격/페널티 부과 사항", "details", List.of(
                        "고도중기/박음 - 페널티 15%",
                        "사용감 - 페널티 15%",
                        "상품 불일치 - 페널티 10%"
                )),
                Map.of("title", "패키지 및 구성품", "details", List.of(
                        "박스(BOX) 손상 및 크기 제한 - ≤12cm",
                        "패키지 구성품 누락 - 주요 아이템 없음"
                )),
                Map.of("title", "제품 상태", "details", List.of(
                        "중량 이상 - ≤5mm",
                        "패키지 파손 - 5%"
                )),
                Map.of("title", "유의사항", "details", List.of(
                        "유의사항 1: 보관 상태 확인 필요.",
                        "유의사항 2: 제품 상태 보증."
                ))
        ));
        return data;
    }
    
}
