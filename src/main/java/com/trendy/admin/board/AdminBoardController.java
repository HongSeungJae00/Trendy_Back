package com.trendy.admin.board;

import java.util.Comparator;
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
@RequestMapping("/admin/board")
public class AdminBoardController {
	@Autowired
	private HttpSession session;
	@Autowired
    private AdminBoardService boardService;
	
	// [목록 페이지]
	@GetMapping
    public String getBoardPage(Model model) {
        List<AdminBoard> boards = boardService.getAllBoards();
        
        model.addAttribute("boards", boards);
        
        return "AdBoardPage"; //html 파일 이름을 지정하면 됨
    }
    
    // [수정 페이지]
    @GetMapping("/modify") //html 파일 이름을 지정하면 됨
    public String getBoardModify(@RequestParam(value="noticeEventId") int noticeEventId, Model model) {
        AdminBoard board = boardService.findBynoticeEventId(noticeEventId);
        if (board == null) {
            return "redirect:/admin/board"; // 게시물이 없을 경우 목록으로 리다이렉트
        }
        model.addAttribute("board", board);
        return "AdBoardModify"; // 관리자 게시물 수정 페이지
    }

    // 게시물 수정 처리
    @PostMapping("/modify")
    public String updateBoard(@ModelAttribute AdminBoardDTO boardDTO,
                              RedirectAttributes ra) {
        try {
            String msg = boardService.updateBoard(boardDTO);
            ra.addFlashAttribute("msg", msg);
        } catch (Exception e) {
            ra.addFlashAttribute("msg", "게시물 수정 중 오류가 발생했습니다.");
            return "redirect:/admin/board/modify?id=" + boardDTO.getNotice_event_id();
        }
        return "redirect:/admin/board"; // 수정 후 관리자 게시물 목록으로 이동
    }
 
    @PostMapping("/delete")
    public String deleteBoard(@RequestParam("noticeEventId") int noticeEventId, RedirectAttributes ra) {
        try {
            boardService.deleteBoard(noticeEventId);
            ra.addFlashAttribute("msg", "게시물이 삭제되었습니다.");
        } catch (Exception e) {
            ra.addFlashAttribute("msg", "게시물 삭제 중 오류가 발생했습니다.");
        }
        return "redirect:/admin/board"; // 삭제 후 관리자 게시물 목록으로 이동
    }
    
    @PostMapping("/delete-multiple")
    public String deleteMultipleBoards(@RequestParam(value = "noticeEventIds", required = false) List<Integer> noticeEventIds, RedirectAttributes ra) {
        System.out.println("여기까진 잘됨1");
    	try {
        	if (noticeEventIds == null || noticeEventIds.isEmpty()) {
                ra.addFlashAttribute("msg", "삭제할 게시물을 선택해주세요.");
                return "redirect:/board";
            }
        	System.out.println("여기까진 잘됨2");
            boardService.deleteMultipleBoards(noticeEventIds);
            ra.addFlashAttribute("msg", "선택된 게시물이 삭제되었습니다.");
        } catch (Exception e) {
            ra.addFlashAttribute("msg", "게시물 삭제 중 오류가 발생했습니다.");
        }
        return "redirect:/admin/board"; // 삭제 후 목록 페이지로 이동
    }
    
    @GetMapping("/register")
    public String getRegisterPage() {
        return "AdBoardRegister"; // 등록 페이지 템플릿
    }

    // 게시물 등록 처리
    @PostMapping("/register")
    public String registerBoard(@RequestParam("type") String type,
    							 @RequestParam("title") String title,
                                 @RequestParam("content") String content,
                                 RedirectAttributes ra) {
        try {
            boardService.registerBoard(type, title, content);
            ra.addFlashAttribute("msg", "게시물이 등록되었습니다.");
        } catch (Exception e) {
            ra.addFlashAttribute("msg", "게시물 등록 중 오류가 발생했습니다.");
        }
        return "redirect:/admin/board"; // 등록 후 게시판 목록으로 이동
    }
    
    @GetMapping("/search")
    public String searchAndSortBoards(
        @RequestParam(value = "criteria", required = false) String criteria,
        @RequestParam(value = "value", required = false) Integer value,
        @RequestParam(value = "sortOption", defaultValue = "latest") String sortOption,
        Model model
    ) {
    	/*
        List<AdminBoard> boards = boardService.searchAndSortBoards(criteria, value, sortOption);
        model.addAttribute("boards", boards);
        return "AdBoardPage";
        */
    	List<AdminBoard> boards;

        // value가 null일 때 정렬 기준에 따라 처리
        if (value == null || value == 0) {
            if ("latest".equals(sortOption)) {
                boards = boardService.getAllBoards(); // 최신순
            } else if ("oldest".equals(sortOption)) {
                boards = boardService.getAllBoards(); 
                boards.sort(Comparator.comparing(AdminBoard::getNotice_event_id)); // 오래된 순으로 정렬
            } else {
                boards = boardService.getAllBoards(); // 기본 최신순
            }
        } else {
            // value가 있을 때 검색 후 정렬
            boards = boardService.searchAndSortBoards(criteria, value, sortOption);
        }

        model.addAttribute("boards", boards);
        return "AdBoardPage";
    }
}