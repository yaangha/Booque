package site.book.project.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Cart;
import site.book.project.dto.CartDto;
import site.book.project.service.CartService;

@Slf4j
@SpringBootTest
public class CartRepositoryTests {

    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private CartService cartService;
    
    @Test
    @Transactional
    public void test() {
        Assertions.assertNotNull(cartRepository);
        
        Assertions.assertNotNull(cartService);
        
        

//        List<Cart> cart = cartService.readAll();
//        for(Cart c: cart) {
//            log.info("cccart {}" , c.getBook().getAuthor());
//        }
        
        
    }
    
    
    
}
