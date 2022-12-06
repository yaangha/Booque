package site.book.project.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.BookComment;
import site.book.project.domain.Post;
import site.book.project.service.BookService;
import site.book.project.service.PostService;

@Slf4j
@SpringBootTest
public class BookRepositoryTests {
	
	@Autowired
	private  PostRepository postRepository;
	@Autowired
	private  PostService postService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookCommentRepository bookCommentRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
//	@Test
//	public void test() {
//		Assertions.assertNotNull(postRepository);
//		 // bookbookid를 통해 얻어오기
//		List<Post> postList = postRepository.findByBookBookId(1);
//		for(Post p : postList) {
//			log.info(p.toString());
//		}
//		
//		Assertions.assertNotNull(bookService);
//		
//		List<String> content = bookService.contentList(1);
//		for(String s : content) {
//			log.info(s);
//		}
//		
//		log.info(bookService.scoreAvg(1).toString());
//		
//	}
	
//	@Test
	public void testComment() {
	    // 최신순, 오래된순, 좋아요 순
	    Assertions.assertNotNull(bookCommentRepository);
	    List<BookComment> list =  bookCommentRepository.findByBookBookIdOrderByCreatedTimeDesc(1);
	    for(BookComment b : list) {
	        log.info("comment 1번 책= {}, 시간 {}",b, b.getCreatedTime() );
	        
	        
	    }
	    
//	    List<BookComment> com =  bookCommentRepository.findAll();
//	    
//	    
//	    for(BookComment b : com) {
//	        log.info("comment = {}", b.getCommentContent());
//	    }
	    
	    
	}
	
	@Test
	public void testFindAuthor() {
	    Assertions.assertNotNull(bookRepository);
	    List<Book> list = bookRepository.findAllByAuthor("김영하");
	    
	    for (Book b : list) {
	        log.info("bookName", b.getBookName());
	    }
	    
	}
	
	
}
