package site.book.project.web;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.dto.SearchListDto;
import site.book.project.dto.SearchQueryDataDto;
import site.book.project.dto.SearchReadDto;
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
        
        // 리뷰 수 카운트
        List<SearchListDto> reviewCount = new ArrayList<>(); 
        for (Book b : searchList) {
            Integer count = postService.countPostByBookId(b.getBookId());
            
            SearchListDto reviewElement = SearchListDto.builder().BookId(b.getBookId()).reviewCount(count).build();
            reviewCount.add(reviewElement);
        }
        
        // 리뷰순 정렬할 때 List 직접 재정렬
        if (dto.getOrder().equals("reviewCount")) {
            List<SearchReadDto> list = new ArrayList<>();
            for (Book s : searchList) {
                for (SearchListDto l : reviewCount) {
                    if (s.getBookId() == l.getBookId()) {
                        SearchReadDto listElement = SearchReadDto.builder().bookId(s.getBookId()).bookName(s.getBookName())
                                .author(s.getAuthor()).publisher(s.getPublisher()).publishedDate(s.getPublishedDate())
                                .prices(s.getPrices()).bookImage(s.getBookImage()).reviewCount(l.getReviewCount()).build();
                        list.add(listElement);
                    }
                }
            }
            
            // 리뷰순으로 오름차순 정렬
            list.sort(new Comparator<SearchReadDto>() {
                @Override
                public int compare(SearchReadDto arg0, SearchReadDto arg1) {
                    int reviewCount0 = arg0.getReviewCount(); 
                    int reviewCount1 = arg1.getReviewCount();
                    
                    if(reviewCount0 == reviewCount1) return 0;
                    else if(reviewCount0 > reviewCount1) return -1;
                    else return 1;
                }
            });
            model.addAttribute("searchList", list);
            model.addAttribute("storedType", type);
            model.addAttribute("storedKeyword", keyword);
            model.addAttribute("reviewCount", reviewCount);
            return "/search";
        }
        
        
        model.addAttribute("searchList", searchList);
        model.addAttribute("storedType", type);
        model.addAttribute("storedKeyword", keyword);
        model.addAttribute("reviewCount", reviewCount);
        return "/search";
    }
    
    
}
