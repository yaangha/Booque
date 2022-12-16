package site.book.project.web;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.BookHits;
import site.book.project.dto.SearchListDto;
import site.book.project.dto.SearchQueryDataDto;
import site.book.project.dto.SearchReadDto;
import site.book.project.service.BookHitsService;
import site.book.project.service.PostService;
import site.book.project.service.SearchService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
@Slf4j
public class SeacrhContoller {
    
    private final SearchService searchService;
    private final PostService postService;
    private final BookHitsService bookHitsService;
    
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
        
        // 리뷰순, 조회수 순 정렬할 때 List 직접 재정렬
        if (dto.getOrder().equals("reviewCount")) { // 리뷰순 정렬
            List<SearchReadDto> list = new ArrayList<>();
            for (Book s : searchList) {
                for (SearchListDto l : reviewCount) {
                    if (s.getBookId().equals(l.getBookId())) {
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
        } else if(dto.getOrder().equals("hitCount")) { // 조회수 순 정렬
            List<BookHits> hitCount = bookHitsService.readAllBookIdHitCount();
            
            List<SearchReadDto> list = new ArrayList<>();
            SearchReadDto listElement = null;
            for (Book s : searchList) {
                for (BookHits h : hitCount) {
                    if (h.getBookId().equals(s.getBookId())) { // 검색 결과 중에 조회수 정보가 있으면 hit 정보를 그 데이터로 저장
                        boolean isDouble = isNotDouble(s.getBookId(), list); // 값이 존재하면 DB에 있는 hit으로 바꿔 줘야하기 때문에
                        if (isDouble == false) {
                            isDouble = true;
                            if (isDouble) {
                                for (SearchReadDto t : list) {
                                    if (t.getBookId().equals(s.getBookId())) {
                                        t.setHit(h.getHit()); // 0으로 되어 있던 hit 순을 DB에 있는 hit으로 변경
                                        break;
                                    }
                                }
//                                log.info("같지만 0을 getHit으로 바꿔주는 시점={},{}",  s.getBookId(), h.getBookId());
                                break;
                            } 
                        } else {
                        listElement = SearchReadDto.builder().bookId(s.getBookId()).bookName(s.getBookName())
                                .author(s.getAuthor()).publisher(s.getPublisher()).publishedDate(s.getPublishedDate())
                                .prices(s.getPrices()).bookImage(s.getBookImage()).hit(h.getHit()).build();
                        list.add(listElement);
//                        log.info("같으면 추가해주는거 빠져나가는 시점={},{}",  s.getBookId(), h.getBookId());
                        break;
                        }
                    }  else if (isNotDouble(s.getBookId(), list)) { 
                        // 검색 결과 중에 조회수 정보가 없어야하며, 리스트에도 저장이 안되어 있을 경우 hit 정보를 0으로 저장
                            listElement = SearchReadDto.builder().bookId(s.getBookId()).bookName(s.getBookName())
                                    .author(s.getAuthor()).publisher(s.getPublisher()).publishedDate(s.getPublishedDate())
                                    .prices(s.getPrices()).bookImage(s.getBookImage()).hit(0).build();
                            list.add(listElement);
//                        log.info("다르면 추가해주는거 빠져나가는 시점={},{}",  s.getBookId(), h.getBookId());
                        continue;
                     }
                    }
            }
            
            // 조회수 순으로 오름차순 정렬
            list.sort(new Comparator<SearchReadDto>() {
                @Override
                public int compare(SearchReadDto arg0, SearchReadDto arg1) {
                    int hitCount0 = arg0.getHit() ;
                    int hitCount1 = arg1.getHit();
                    
                    if(hitCount0 == hitCount1) return 0;
                    else if(hitCount0 > hitCount1) return -1;
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
    
    // 조회수 순 리스트 중복 체크(리스트에도 저장이 안되어 있는지 체크) - 저장이 되어 있으면 false, 안되어 있으면 true
    public boolean isNotDouble(Integer bookId, List<SearchReadDto> compareList) {
        boolean result = true;
        List<SearchReadDto> cL = compareList;
        for (SearchReadDto b : cL) {
            if (b.getBookId().equals(bookId)) {
                result = false;
            }
        }
        return result;
    }
}
