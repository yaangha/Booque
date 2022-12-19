package site.book.project.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.Post;
import site.book.project.dto.SearchReadDto;
import site.book.project.service.BookService;
import site.book.project.service.HomeService;
import site.book.project.service.PostService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final HomeService homeService;
    private final BookService bookService;
    private final PostService postService;
    
    
    @GetMapping("/")
    public String home(Model model) {
        log.info("home()");
        
        // 전체 책 별점순 1~10위
        List<Book> list = homeService.readAllRankingOrderByBookScore();
//        for (Book b : list) {
//            log.info("id={},score={}", b.getBookId(), b.getBookScore());
//        }
        
        // 전체 책 리뷰많은순 1~10위
        List<Book> postList = homeService.readAllRankingOrderByPostReview();
        for (Book p : postList) {
            log.info("id={},review={}", p.getBookId(), p.getPostCount());
        }
        
        // 분야별 도서(5분야) 상동
        
        
        // 전체 포스트(리뷰) 중 댓글이 많이 달린 순 1~5위
        
        
        // 전체 포스트(조회수순) 1~5위
        
        model.addAttribute("top10ScoreList", list);
        model.addAttribute("top10ReviewList", postList);
        return "home";
    }

    
}
