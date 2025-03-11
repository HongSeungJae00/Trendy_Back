package com.trendy.main;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trendy.mypage.event.EventDTO;

@Controller
public class MainController {

    // @GetMapping("/")
    // public String Page(Model model) {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser");
    //     model.addAttribute("isAuthenticated", isAuthenticated);
    //     return "/main";
    // }
    
    @GetMapping("/main")
    public String mainPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser");
        model.addAttribute("isAuthenticated", isAuthenticated);
        return "/main";
    }
    
    @GetMapping("/event")
    public String eventPage(Model model) {
        // 이벤트 게시판 데이터를 전달할 경우
        model.addAttribute("eventTitle", "2024 BEST KICK");
        model.addAttribute("events", getEventData()); // 이벤트 데이터 목록
        return "/event";
    }
    
 // 이벤트 데이터를 반환하는 메서드 (임시)
    private List<EventDTO> getEventData() {
        List<EventDTO> events = new ArrayList<>();
        events.add(new EventDTO("온 러닝 x PAF Cloudmonster 2 Black Magnet", "299,000원", "450,000원", "2024/05/03", "/images/shoe1.png"));
        events.add(new EventDTO("조던 1 x 트래비스 스캇 Olive", "249,000원", "550,000원", "2024/02/10", "/images/shoe2.png"));
        return events;
    }
    
    
}
