package site.book.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import site.book.project.domain.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

    // (하은) 작가의 다른 책 정보 찾기
    List<Book> findAllByAuthor(String author);

}
