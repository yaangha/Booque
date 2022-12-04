package site.book.project.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.service.BookService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BookDetailController {
// 책 상세 페이지, 
// book, bookcomment, post, 책 홍보(테이블을 만들어야 함)
// 
	
	private final BookService bookService;
	
	@GetMapping("/detail")
	public void detail(Integer id, Model model) {
		log.info("책 상세(bookId={})",id);
		Book book = bookService.read(id);
		// POST dto 만들기(userid, postid, content, score, title) TODO
		
		// post에 있는 평점 double말고 int로 보내야 할지
		// 그 점수를 어떻게 별점으로 처리해야할지 고민
		// for문을 통해서 숫자를 그림으로 표현? 참고해서 고치기
		double score = bookService.scoreAvg(id);
		
		// 책 정보 넘기기 
		model.addAttribute("book", book);
		model.addAttribute("score", score);
		// comment 넘기기
		// post 넘기기(post글 필요)
		
	}
	
	
}
