package com.trendy.admin.board;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("unused")
public class AdminBoardService {
	@Autowired
	private HttpSession session;
	@Autowired
	private AdminBoardRepository boardRepository;

	// [목록 페이지]
	public List<AdminBoard> getAllBoards() {
		return boardRepository.findAllByOrderByNoticeEventIdDesc();
	}
	
	// [수정 페이지]
	// 게시물 조회
	public AdminBoard findBynoticeEventId(int noticeEventId) {
		return boardRepository.findBynoticeEventId(noticeEventId).orElse(null);
	}

	// 게시물 수정 처리
	public String updateBoard(AdminBoardDTO boardDTO) {
		AdminBoard board = boardRepository.findBynoticeEventId(boardDTO.getNotice_event_id()).orElse(null);
		if (board == null) {
			return "게시물이 존재하지 않습니다.";
		}

		// 제목 및 내용 업데이트
		board.setType(boardDTO.getType());
		board.setTitle(boardDTO.getTitle());
		board.setContent(boardDTO.getContent());
		board.setUserId(1L);

//		// 이미지 파일 처리
//        if (imageFile != null && !imageFile.isEmpty()) {
//            String imagePath = fileService.saveFile(imageFile);
//            board.setImagePath(imagePath);
//        }
		 
		boardRepository.save(board);
		return "게시물이 성공적으로 수정되었습니다.";
	}

	// 게시물 삭제
	public void deleteBoard(int noticeEventId) {
		boardRepository.deleteById(noticeEventId);
	}
	
	
	// 다중 게시물 삭제
	public void deleteMultipleBoards(List<Integer> noticeEventIds) {
		if (noticeEventIds == null || noticeEventIds.isEmpty()) {
            throw new IllegalArgumentException("삭제할 게시물이 선택되지 않았습니다.");
        }
	    boardRepository.deleteAllByNoticeEventIdIn(noticeEventIds);
	}
	
	 // 게시물 등록
    public void registerBoard(String type, String title, String content) {
    	AdminBoard newBoard = new AdminBoard();
        newBoard.setUsername("관리자");
        newBoard.setType(type);
        newBoard.setTitle(title);
        newBoard.setContent(content);
        newBoard.setUserId(1L);

        //newBoard.setType("일반"); // 기본 유형 설정
        boardRepository.save(newBoard);
    }
    
    public List<AdminBoard> searchBoards(String criteria, int value) {
	    List<AdminBoard> boards;

	    // 검색 처리
	    if ("noticeEventId".equals(criteria)) {
	        boards = boardRepository.findAllByNoticeEventId(value);
	    } else {
	    	boards = boardRepository.findAll(); // 기본 전체 검색
	    }

	    return boards;
	}

	public List<AdminBoard> sortBoards(List<AdminBoard> boards, String sortOption) {
	    // 정렬 처리
	    switch (sortOption) {
	        case "latest":
	        	boards.sort(Comparator.comparing(AdminBoard::getNotice_event_id).reversed());
	            break;
	        case "oldest":
	        	boards.sort(Comparator.comparing(AdminBoard::getNotice_event_id));
	            break;
	        default:
	            break; // 기본 정렬 유지
	    }

	    return boards;
	}
	public List<AdminBoard> searchAndSortBoards(String criteria, int value, String sortOption) {
        List<AdminBoard> boards = searchBoards(criteria, value);
        return sortBoards(boards, sortOption);
    }
	
	public List<AdminBoard> getAllBoardsSorted(String sortOption) {
	    List<AdminBoard> boards = getAllBoards(); // 기본적으로 최신순
	    if ("oldest".equals(sortOption)) {
	        boards.sort(Comparator.comparing(AdminBoard::getNotice_event_id)); // 오래된 순 정렬
	    }
	    return boards;
	}
}