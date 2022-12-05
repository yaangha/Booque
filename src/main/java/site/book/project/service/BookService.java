package site.book.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

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

	// 별점 소숫점 첫째 자리까지 완.
	public Double scoreAvg(Integer bookId) {
	    log.info("별점 계산 bookid={}", bookId);
		List<Post> list =postRepository.findByBookBookId(bookId);
		Integer sum=0;
		
		for(Post p : list) {
			sum+=p.getMyScore();
			log.info("합계{}",sum);
		}
		
		double avg = sum/(double)list.size(); 
		
		log.info("평균 별점{}",avg);
		
		avg = (Math.round(avg*10))/10.0;
		
		log.info("평균 별점{}",avg);
		return avg;
	}
	
	
	
}
