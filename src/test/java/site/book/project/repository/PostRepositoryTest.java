package site.book.project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Post;
import site.book.project.domain.User;


@SpringBootTest
@Slf4j
public class PostRepositoryTest {
    
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void test() {
        User user1 = userRepository.findById(1).get();
        Post entity = Post.builder()
                .title("제목").postContent("내용").myScore(3)
                .user(user1).build();
       log.info("save 전: {} | {} | {}", entity, entity.getCreatedTime(), entity.getModifiedTime());
       
       entity = postRepository.save(entity);
       
       log.info("save 후: {} | {} | {}", entity, entity.getCreatedTime(), entity.getModifiedTime());
       // TODO: 유저 정보에 대한 글 저장까진 됨. -> basetime이 null로 들어감.
       
    }
}
