package site.book.project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import site.book.project.domain.User;


@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void test() {
        User user1 = User.builder().address("주소")
                .email("0").name("123").nickName("0").password("123").phone("123").username("00")
                .build();
        userRepository.save(user1);
        
    }
    
    
    
}
