package site.book.project.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Post;
import site.book.project.domain.PostReply;
import site.book.project.domain.User;

@Slf4j
@SpringBootTest
@TestMethodOrder(value= MethodOrderer.OrderAnnotation.class)
public class ReplyRepositoryTest {
    
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    
    @Test
    @Order(1)
    public void testSave() {
        Post post = postRepository.findById(1).get();
        User user = userRepository.findById(1).get();
        PostReply reply = PostReply.builder()
                .post(post)
                .user(user)
                .replyContent("댓글 작성 테스트")
                .build();
        log.info("save 전: {} | {}", reply, reply.getCreatedTime());
        reply = replyRepository.save(reply);
        
        log.info("save 후: {} | {} | {}", reply, reply.getCreatedTime(), reply.getModifiedTime());
        
    }
    
    @Transactional
    @Test
    @Order(2)
    public void testFindAll() {
        
        List<PostReply> list = replyRepository.findAll();
        for (PostReply r : list) {
            log.info(r.toString());
        }   
    }

}
