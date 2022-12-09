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
import site.book.project.dto.SearchQueryDataDto;
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
    public String search(SearchQueryDataDto dto, Model model) {
        log.info("request : search(type={}, keyword={})", dto.getType(), dto.getKeyword());
        
        List<Book> searchList = searchService.search(dto.getType(), dto.getKeyword());
        List<SearchQueryDataDto> queryData = new ArrayList<>();
        queryData.add(0, dto);
        for (SearchQueryDataDto s : queryData) {
            log.info("저장 후 : search(type={}, keyword={})", s.getType(), s.getKeyword());
        }
        model.addAttribute("searchList", searchList);
        model.addAttribute("queryData", queryData);
        return "/search";
    }
    
    @GetMapping("/ss")
    public String research(SearchQueryDataDto dto, Model model) {
        log.info("request : search(type={}, keyword={}, order={})", dto.getType(), dto.getKeyword(), dto.getOrder());
        
        List<Book> searchList = searchService.research(dto.getType(), dto.getKeyword(), dto.getOrder());
        List<SearchQueryDataDto> queryData = new ArrayList<>();
        queryData.add(0, dto);
        for (SearchQueryDataDto s : queryData) {
            log.info("재정렬 저장 후 : search(type={}, keyword={})", s.getType(), s.getKeyword());
        }
        model.addAttribute("searchList", searchList);
        model.addAttribute("queryData", queryData);
        return "/search";
    }
    
}
