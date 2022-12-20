package site.book.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lombok.val;
import site.book.project.domain.Book;

public interface CategoryRepository extends JpaRepository<Book, Integer> {
    
    // (지혜) 분야별 도서 (사이드바 링크)
    List<Book> findByCategory(String category);
    
    // (지혜) 국내도서/외국도서 전체 (사이드바 링크)
    List<Book> findByBookgroup(String group);
    
    // (지혜) 국내도서/외국도서 선택 후 -> 하위 카테고리  (사이드바 링크)
    List<Book> findByBookgroupAndCategory(String group, String category);

    // (홍찬) 메인에서 보여줄 Top4 & category별 & 별점순/리뷰순
    List<Book> findTop4ByCategoryOrderByBookScoreDesc(String category);
    List<Book> findTop4ByCategoryOrderByPostCountDesc(String category);
    
    
    
}
