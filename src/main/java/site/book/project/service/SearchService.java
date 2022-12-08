package site.book.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.repository.SearchRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchService {

    private final SearchRepository searchRepository;
    
    public List<Book> search(String type, String keyword) {
        List<Book> list = null;
        
        // 드랍 다운 검색 타입(value 속성):
        if(type.equals("all")) { // 통합 검색
            list = searchRepository.unifiedSearchByKeyword(keyword);
            return list;
        }  else if (type.equals("do")) { // 국내 도서 검색
            list = searchRepository.domesticSearchByKeyword(keyword);
            return list;
        } else if (type.equals("fo")) { // 외국 도서 검색
            list = searchRepository.foreignSearchByKeyword(keyword);
            return list;
        } else if (type.equals("au")) { // 저자 검색
            list = searchRepository.authorSearchByKeyword(keyword);
            return list;
        }
        return list;
    }
    
}
