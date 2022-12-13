package site.book.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.repository.SearchRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchService {

    private final SearchRepository searchRepository;
    
    @Transactional(readOnly = true)
    public List<Book> search(String type, String keyword) {
        List<Book> list = null;

        // 드랍 다운 검색 타입(value 속성):
        if(type.equals("all")) { // 통합 검색
            return list = searchRepository.unifiedSearchByKeyword(keyword);
        }  else if (type.equals("do")) { // 국내 도서 검색
            return list = searchRepository.domesticSearchByKeyword(keyword);
        } else if (type.equals("fo")) { // 외국 도서 검색
            return list = searchRepository.foreignSearchByKeyword(keyword);
        } else if (type.equals("au")) { // 저자 검색
            return list = searchRepository.authorSearchByKeyword(keyword);
        }
        return list;
    }
    
    @Transactional(readOnly = true)
    public List<Book> research(String type, String keyword, String order){
        List<Book> list = null;
        switch(order) {
        case "highPrice": // 최고가순 정렬
            list = searchRepository.researchOrderByHighPrice(keyword);
            break;
        case "lowPrice": // 최저가순 정렬
            list = searchRepository.researchOrderByLowPrice(keyword);
            break;
        case "publishedDate": // 신상품순 정렬
            list = searchRepository.researchOrderByPublishedDate(keyword); 
            break;
        }

        return list;
    }
    
}
