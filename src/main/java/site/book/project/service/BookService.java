package site.book.project.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.Post;
import site.book.project.repository.BookRepository;
import site.book.project.repository.PostRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {
    //

	private final BookRepository bookRepository;
	private final PostRepository postRepository;
	
	// BookId로 Book 꺼냄
	public Book read(Integer id) {
		return bookRepository.findById(id).get();
	}
	// 별점순, 최신순 총 4개의 문장이 필요함
	// 유저번호(나중에블로그로 넘어가야함), 유저이름, 제목, 컨텐트, 별점 DTO 필요
	// 
	public List<String> contentList(Integer bookId){
		List<Post> list =postRepository.findByBookBookId(bookId);
		List<String> contentList = new ArrayList<>();
		
		for(Post p : list) {
			contentList.add(p.getPostContent());
			
		}
		return contentList;
	}

//	public void update(Integer bookScore, Integer) {
//		Book entity = bookRepository
//	}
	
	
	
	// 별점 소숫점 첫째 자리까지 완.
	@Transactional
	public Double scoreAvg(Integer bookId) {
		List<Post> list =postRepository.findByBookBookId(bookId);
		Book entity = bookRepository.findById(bookId).get();
		Integer sum=0;
		if(entity.getBookScore() == null) {
			sum = 0;
		} else {
			sum = entity.getBookScore()/10; // bookScore는 10~50까지의 수임
		}
		
		for(Post p : list) {
			sum+=p.getMyScore();
		}
		
		double avg = sum/(double)list.size(); 
		Integer s =Integer.parseInt(Math.round(avg*10)+"");
		
		
		entity.update(s);
		
		
		avg = (Math.round(avg*10))/10.0;
		return avg;
	}
	
    // (하은) 작가의 다른 책 정보 read
    public List<Book> readAuthor(String author) {
        log.info("author={}", author);
        List<Book> authorOtherBook = bookRepository.findAllByAuthor(author);

        return authorOtherBook;
    }
    public List<Book> read() {
        
        return bookRepository.findByOrderByBookIdDesc();
    }   
	
}
