package site.book.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import site.book.project.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

    List<Post> findByOrderByPostIdDesc();

	// bookid로 post글 꺼내서 책상세페이지에서 사용할 예정
	// select * from posts where book_book_id = 1;
	List<Post> findByBookBookId(Integer bookId);

}
