package site.book.project.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.repository.SearchRepository;
import site.book.project.service.SearchService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
@Slf4j
public class SeacrhContoller {
    
    private final SearchService searchService;
    
    @GetMapping("")
    public String search() {
        log.info("MainSearch()");
        return "/search";
    }
    
    @GetMapping("/s")
    public String search(String type, String keyword, Model model) {
        log.info("search(type={}, keyword={})", type, keyword);
        
        List<Book> searchList = searchService.search(type, keyword);
        
        model.addAttribute("searchList", searchList);
        return "/search";
    }
    
    
}
