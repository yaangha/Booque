package site.book.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import site.book.project.domain.BookWish;

public interface BookWishRepository extends JpaRepository<BookWish, Integer> {
    
    // (하은) userId로 찜한 리스트 찾기
    List<BookWish> findAllByUserId(Integer id);
    
}
