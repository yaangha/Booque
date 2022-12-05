package site.book.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import site.book.project.domain.BookComment;

public interface BookCommentRepository extends JpaRepository<BookComment, Integer> {

    
    
//    // 좋아요순, 최신순, 별점순?
//    // 최근순
    List<BookComment> findByBookBookIdOrderByCreatedTimeDesc(Integer bookId);
//    // 오래된 순
    List<BookComment> findByBookBookIdOrderByCreatedTime(Integer bookId);
//    // 좋아요 순
    

    
    
    
    
}
