package site.book.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.repository.CategoryRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    @Transactional(readOnly = true)
    public List<Book> sort(String group, String category) {
        List<Book> list = null;
        
        if ((group == null) & category == null) {    // 모든 책 검색
            list = categoryRepository.findAll();
        } else if(group == null) {   // 소분류별 검색
            
            if(category.equals("경제")) {
                category="경제/경영";
            } else if (category.equals("시")) {
                category="시/에세이";
                }
            
            list = categoryRepository.findByCategory(category);
        } else if (category == null) {    // 도서 대분류(국내도서/외국도서)별 검색
            list = categoryRepository.findByBookgroup(group);
        } else {   // 도서 대분류-소분류별 검색
            
            if(category.equals("경제")) {
                category="경제/경영";
            } else if (category.equals("시")) {
                category="시/에세이";
                }
            
            list = categoryRepository.findByBookgroupAndCategory(group, category);
        }
        
        return list;
    }
    
}
