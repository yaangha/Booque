package site.book.project.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Cart;
import site.book.project.service.CartService;

@Slf4j
@SpringBootTest
public class CartRepositoryTests {

    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private CartService cartService;
    
    @Test
    public void test() {
        Assertions.assertNotNull(cartRepository);
        
        Assertions.assertNotNull(cartService);
        List<Cart> list = cartRepository.findAll();
        log.info("읽히니?");
        
        for(Cart c : list) {
            log.info("cart 카트!!!! c ={}",c);
            
        }
    }
    
    
    
}
