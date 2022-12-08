package site.book.project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.service.BookService;
import site.book.project.service.PostService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final BookService bookService;
    private final PostService postService;
    
    @GetMapping("/")
    public String home() {
        log.info("home()");
        
        return "home";
    }

}
