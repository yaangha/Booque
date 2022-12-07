package site.book.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Post;
import site.book.project.dto.PostCreateDto;
import site.book.project.dto.PostReadDto;
import site.book.project.repository.PostRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    
    // Post 리스트 전체  TODO 유저별 전체리스트 ? 
    @Transactional(readOnly = true)
    public List<Post> read(){
        log.info("read()");
        
        return postRepository.findByOrderByPostIdDesc();
    }
    
    public Post create(PostCreateDto dto) {
        log.info("create(dto = {})",dto);
        
        Post entity = postRepository.save(dto.toEntity());
        return entity;
    }

    @Transactional(readOnly = true)
    public Post read(Integer postId) {
        log.info("read(postId = {})", postId);
        
        return postRepository.findById(postId).get();
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
