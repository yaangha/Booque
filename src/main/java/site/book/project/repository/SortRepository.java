package site.book.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lombok.val;
import site.book.project.domain.Book;

public interface SortRepository extends JpaRepository<Book, Integer> {
    
    // (지혜) 분야별 도서 (사이드바 링크)
    List<Book> findByCategory(String category);
    
    // (지혜) 국내도서/외국도서 전체 (사이드바 링크)
    List<Book> findByBookgroup(String group);
    
    // (지혜) 국내도서/외국도서 선택 후 -> 하위 카테고리  (사이드바 링크)
    List<Book> findByBookgroupAndCategory(String group, String category);
    
    
    
}
