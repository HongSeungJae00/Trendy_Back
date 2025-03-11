package com.trendy.admin.notice;

import java.util.List;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/admin/notice")
@RequiredArgsConstructor
public class AdminNoticeController {
	@Autowired
	private HttpSession session;
	@Autowired
    private AdminNoticeService noticeService;
	@Autowired
	private CommentsService commentsService;
	
	// [목록 페이지]
	@GetMapping
    public String getNoticePage(Model model) {
        List<AdminNotice> notices = noticeService.getAllNotices();
        
        model.addAttribute("notices", notices);
        
        return "AdNoticePage"; //html 파일 이름을 지정하면 됨
    }
    
    // [수정 페이지]
	@GetMapping("/detail")
	public String getNoticeDetail(@RequestParam("inquiryId") Long inquiryId, Model model) {
	    AdminNoticeDTO notice = noticeService.getNoticeDetail(inquiryId);
	    List<Comments> comments = commentsService.getCommentsByNoticeId(inquiryId);
	    
	    // inquiryId를 model에 추가
	    model.addAttribute("inquiryId", inquiryId);
	    model.addAttribute("notice", notice);
	    model.addAttribute("comments", comments);
	    
	    System.out.println("상세 페이지 inquiryId: " + inquiryId); // 디버깅용
	    return "AdNoticeDetail";
	}

	// 댓글 작성을 위한 새로운 엔드포인트 추가
	@PostMapping("/comments")
    public ResponseEntity<?> addComment(@RequestBody CommentRequest request) {
        try {
            System.out.println("댓글 요청 받음: " + request); // 디버깅용 로그
            Comments savedComment = commentsService.addComment(
                request.getInquiryId(),
                request.getUserId(),
                request.getContent()
            );
            return ResponseEntity.ok(savedComment);
        } catch (Exception e) {
            System.err.println("댓글 저장 중 에러: " + e.getMessage()); // 디버깅용 로그
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .body("댓글 저장 중 오류가 발생했습니다.");
        }
    }


    @PostMapping("/delete")
    public String deleteNotice(@RequestParam("inquiryId") Long inquiryId, RedirectAttributes ra) {
        try {
        	noticeService.deleteNotice(inquiryId);
            ra.addFlashAttribute("msg", "게시물이 삭제되었습니다.");
        } catch (Exception e) {
            ra.addFlashAttribute("msg", "게시물 삭제 중 오류가 발생했습니다.");
        }
        return "redirect:/admin/notice"; // 삭제 후 관리자 게시물 목록으로 이동
    }
    
    @PostMapping("/delete-multiple")
    public String deleteMultipleNotices(@RequestParam(value = "inquiryIds", required = false) List<Long> inquiryIds, RedirectAttributes ra) {
        System.out.println("여기까진 잘됨1");
    	try {
        	if (inquiryIds == null || inquiryIds.isEmpty()) {
                ra.addFlashAttribute("msg", "삭제할 게시물을 선택해주세요.");
                return "redirect:/notice";
            }
        	System.out.println("여기까진 잘됨2");
        	noticeService.deleteMultipleNotices(inquiryIds);
            ra.addFlashAttribute("msg", "선택된 게시물이 삭제되었습니다.");
        } catch (Exception e) {
            ra.addFlashAttribute("msg", "게시물 삭제 중 오류가 발생했습니다.");
        }
        return "redirect:/admin/notice"; // 삭제 후 목록 페이지로 이동
    }
    
    @GetMapping("/register")
    public String getRegisterPage() {
        return "AdNoticeRegister"; // 등록 페이지 템플릿
    }
/*
    // 게시물 등록 처리
    @PostMapping("/register")
    public String registerNotice(@RequestParam("type") String type,
    							 @RequestParam("title") String title,
                                 @RequestParam("content") String content,
                                 RedirectAttributes ra) {
        try {
        	noticeService.registerNotice(type, title, content);
            ra.addFlashAttribute("msg", "게시물이 등록되었습니다.");
        } catch (Exception e) {
            ra.addFlashAttribute("msg", "게시물 등록 중 오류가 발생했습니다.");
        }
        return "redirect:/admin/notice"; // 등록 후 게시판 목록으로 이동
    }
    */
    @GetMapping("/list")
    public String getNoticeList(Model model,
                              @RequestParam(name = "category", required = false) String category,
                              @RequestParam(name = "sort", defaultValue = "desc") String sort,
                              @PageableDefault(size = 10) Pageable pageable) {
                              
        Page<AdminNoticeDTO> noticePage;
        
        if (StringUtils.hasText(category)) {
            noticePage = noticeService.getNoticesByCategory(category, sort, pageable);
        } else {
            noticePage = noticeService.getAllNotices(sort, pageable);
        }
        
        model.addAttribute("notices", noticePage);
        return "AdNoticePage";
    }
}