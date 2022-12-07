package site.book.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import site.book.project.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

    List<Post> findByOrderByPostIdDesc();
    
    // 포스트 제목:
    // select * from POSTS where lower(TITLE) like lower(?) order by ID desc
    List<Post> findByTitleIgnoreCaseContainingOrderByPostIdDesc(String title);
    
    // 포스트 내용:
    // select * from POSTS where lower(POSTCONTENT) like lower(?) order by ID desc
    List<Post> findByPostContentIgnoreCaseContainingOrderByPostIdDesc(String postContent);
    
 
}
