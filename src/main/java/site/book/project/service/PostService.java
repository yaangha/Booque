package site.book.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Post;
import site.book.project.dto.PostCreateDto;
import site.book.project.repository.PostRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    
    
    // Post 리스트 전체
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
}
