package site.book.project.service;

import java.util.ArrayList;
import java.util.List;

// import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.Post;
import site.book.project.dto.PostCreateDto;
import site.book.project.dto.PostUpdateDto;
import site.book.project.dto.PostReadDto;
import site.book.project.repository.BookRepository;
import site.book.project.repository.PostRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final BookRepository bookRepository;

    // Post 리스트 전체  TODO 유저별 전체리스트 ? 
    @Transactional(readOnly = true)
    public List<Post> read(){
        log.info("read()");
        
        return postRepository.findByOrderByPostIdDesc();
    }
    
    public Post create(PostCreateDto dto) {
        log.info("create(dto = {})",dto); // 읽어옴. bookId를 Book객체로
        Book book = bookRepository.findById(dto.getBookId()).get();
        
        
        Post entity = postRepository.save(dto.toEntity(book));
        return entity;
    }

    @Transactional(readOnly = true)
    public Post read(Integer postId) {
        log.info("read(postId = {})", postId);
        
        return postRepository.findById(postId).get();
    }

    public void delete(Integer postId) {
        log.info("delete(postId={})",postId);
       
        postRepository.deleteById(postId);
       
    }
   
    @Transactional // readOnly = false(기본값)
    public void update(PostUpdateDto dto) {
        log.info("update(dto={})", dto);
       
        // 메서드에 @Transactional 애너테이션을 사용하고,
        // (1) 수정하기 전의 엔터티 객체를 검색한 후에
        // (2) 검색된 엔터티 객체를 수정하면
        // 메서드가 종료될 때 데이터베이스 테이블에 자동으로 update SQL이 실행됨.
        // PostRepository의 save() 메서드를 명시적으로 호출하지 않아도 됨.
        Post entity = postRepository.findById(dto.getPostId()).get(); // (1)
        entity.update(dto.getTitle(), dto.getPostContent()); // (2)
       
        
    }

    public List<Post> search(String type, String keyword) {
        log.info("search(type= {} keyword={})", type, keyword);
        
        List<Post> list = new ArrayList<>();
        
        switch(type) {
        case "pt":
            list = postRepository.findByTitleIgnoreCaseContainingOrderByPostIdDesc(keyword);
            break;
        case "pc":
            list = postRepository.findByPostContentIgnoreCaseContainingOrderByPostIdDesc(keyword);
            break;
        
        }
        
        return list;
    }


    
    // choi 1207 책 상세 post 최신순, 별점 높은순, 별점 낮은순 => Ajax로 할 예정

	public List<Post> findBybookId(Integer bookId) {
	    
	    // 오래된 순
	    return postRepository.findByBookBookId(bookId);
	}

	// 최신순
	public List<PostReadDto> findDesc(Integer bookId){
	    List<Post> list = postRepository.findByBookBookIdOrderByCreatedTimeDesc(bookId); 
	    return list.stream().map(PostReadDto:: fromEntity).toList();
	}
	// 별점 높은순
	public List<PostReadDto> findScoreDesc(Integer bookId){
	    List<Post> list = postRepository.findByBookBookIdOrderByMyScoreDesc(bookId);
	    
	    return list.stream().map(PostReadDto:: fromEntity).toList();
	}
	
	// 별점 낮은순
	public List<PostReadDto> findScore(Integer bookId){
	    List<Post> list = postRepository.findByBookBookIdOrderByMyScore(bookId);
	    return list.stream().map(PostReadDto:: fromEntity).toList();
	}

}
