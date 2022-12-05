package site.book.project.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
	
	@Test
	public void test() {
		Assertions.assertNotNull(postRepository);
		 // bookbookid를 통해 얻어오기
		List<Post> postList = postRepository.findByBookBookId(1);
		for(Post p : postList) {
			log.info(p.toString());
		}
		
		Assertions.assertNotNull(bookService);
		
		List<String> content = bookService.contentList(1);
		for(String s : content) {
			log.info(s);
		}
		
		log.info(bookService.scoreAvg(1).toString());
		
	}
	
	
	
}
