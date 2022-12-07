package site.book.project.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Post;
import site.book.project.domain.User;
import site.book.project.service.PostService;

@Slf4j
@SpringBootTest
public class PostRepositoryTests {

   @Autowired
   private PostRepository postRepository;
   
   @Autowired
   private PostService postService;
   
   @Test
   public void testSave() {
   
//       User user1 = User.builder().username("user1").password("111").nickName("0").email("dd@n").phone("1").name("김").address("경기").build();
//       Post entity = Post.builder().user(user1).title("어린왕자").postContent("재밋다").postWriter("루피").myScore(5).build();
//       
//       log.info("save 전 {} | {} | {}", entity, entity.getCreatedTime(), entity.getModifiedTime());
//       
//       postRepository.save(entity);   // insert 문장
//       log.info("save 후 {} | {} | {}", entity, entity.getCreatedTime(), entity.getModifiedTime());
       
       
       Assertions.assertNotNull(postRepository);
       Assertions.assertNotNull(postService);
       List<Post> list = postService.findScore(1);
       
       
       for(Post p : list) {
           log.info("시간순 ={}" , p.getMyScore());
           
       }
       List<Post> lists = postService.findScoreDesc(1);
       
       
       for(Post p : lists) {
           log.info("시간순 높은 순={}" , p.getMyScore());
           
       }
       
       
   }
}
