package site.book.project.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.dto.SearchListDto;
import site.book.project.service.BookHitsService;
import site.book.project.service.CategoryService;
import site.book.project.service.PostService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
@Slf4j
public class CategoryContoller {
    
    private final CategoryService categoryService;
    private final PostService postService;
    private final BookHitsService bookHitsService;
    
    @GetMapping("")
    public String sort(String group, String category, Model model) {
        log.info("sort: group={}, category={}", group, category);
   
        
        
        List<Book> list = categoryService.sort(group, category);
        
        List<SearchListDto> reviewCount = new ArrayList<>();
        for (Book b : list) {
            Integer count = postService.countPostByBookId(b.getBookId());
            SearchListDto element = SearchListDto.builder().BookId(b.getBookId()).reviewCount(count).build();
            reviewCount.add(element);
        }
        
        model.addAttribute("searchList", list);
        model.addAttribute("bookgroup", group);
        model.addAttribute("category", category);
        model.addAttribute("reviewCount", reviewCount);
        
        return "/category";
    }
    
}
