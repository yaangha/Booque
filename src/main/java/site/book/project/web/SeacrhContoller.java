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
import site.book.project.domain.Post;
import site.book.project.dto.SearchListDto;
import site.book.project.dto.SearchQueryDataDto;
import site.book.project.service.PostService;
import site.book.project.service.SearchService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
@Slf4j
public class SeacrhContoller {
    
    private final SearchService searchService;
    private final PostService postService;
    
    @GetMapping("")
    public String search() {
        log.info("MainSearch()");
        return "/search";
    }
    
    // 검색 기능
    @GetMapping("/s")
    public String search(SearchQueryDataDto dto, Model model) {
        log.info("request : search(type={}, keyword={})", dto.getType(), dto.getKeyword());
        String type = dto.getType();
        String keyword = dto.getKeyword();
        if (keyword.equals("")) {
            return "/search";
        }
        List<Book> searchList = searchService.search(dto.getType(), dto.getKeyword());
        List<SearchListDto> reviewCount = new ArrayList<>(); 
        for (Book b : searchList) {
            Integer count = postService.countPostByBookId(b.getBookId());
            
            SearchListDto element = SearchListDto.builder().BookId(b.getBookId()).reviewCount(count).build();
            reviewCount.add(element);
        }
        
        model.addAttribute("searchList", searchList);
        model.addAttribute("storedType", type);
        model.addAttribute("storedKeyword", keyword);
        model.addAttribute("reviewCount", reviewCount);
        return "/search";
    }
    
    // 검색 기능 - 검색 결과 재정렬(type, keyword를 가지고 다시 order by ?, ?부분만 원하는 order에 따라 바꿔서 재검색
    @GetMapping("/ss")
    public String research(SearchQueryDataDto dto, Model model) {
        log.info("request : search(type={}, keyword={}, order={})", dto.getType(), dto.getKeyword(), dto.getOrder());
        String type = dto.getType();
        String keyword = dto.getKeyword();
        if (keyword.equals("")) {
            return "/search";
        }
        List<Book> searchList = searchService.research(dto.getType(), dto.getKeyword(), dto.getOrder());
        List<SearchListDto> reviewCount = new ArrayList<>(); 
        for (Book b : searchList) {
            Integer count = postService.countPostByBookId(b.getBookId());
            
            SearchListDto element = SearchListDto.builder().BookId(b.getBookId()).reviewCount(count).build();
            reviewCount.add(element);
        }

        model.addAttribute("searchList", searchList);
        model.addAttribute("storedType", type);
        model.addAttribute("storedKeyword", keyword);
        model.addAttribute("reviewCount", reviewCount);
        return "/search";
    }
}
