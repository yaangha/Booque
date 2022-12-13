package site.book.project.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.Post;
import site.book.project.service.BookService;
import site.book.project.service.PostService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BookDetailController {

	
	private final BookService bookService;
	private final PostService postService;
    
    @GetMapping("/detail")
    public String detail(Integer id, Model model) {
        log.info("책 상세(bookId={})",id);
        
        Book book = bookService.read(id);
        
        // (하은) 동일한 작가 책 정보 넘기기
        List<Book> authorOtherBook = bookService.readAuthor(book.getAuthor());
        log.info("하은 author={}", book.getAuthor());
        model.addAttribute("authorOtherBook", authorOtherBook);
        
        // POST dto 만들기(userid, postid, content, score, title) TODO
        
        // for문을 통해서 숫자를 그림으로 표현? 참고해서 고치기
        double score = bookService.scoreAvg(id);
        
        // 책 정보 넘기기 
        model.addAttribute("book", book);
        model.addAttribute("score", score);
        // comment 넘기기
        // post 넘기기(post글 필요)

        
        // choi 책 한권에 대한 post 정보 받기
        List<Post> postList = postService.findBybookId(id);
        
       
        model.addAttribute("postList", postList );      
        
        return "book/detail";
    }
    
    @GetMapping("/post/create")
    public String create(Integer id, Model model) {
        log.info("책 상세(bookId={})",id);
        
        Book book = bookService.read(id);
        model.addAttribute("book", book);
        
        return "post/create";
    }
	
}
