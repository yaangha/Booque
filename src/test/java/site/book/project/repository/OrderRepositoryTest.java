package site.book.project.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Cart;
import site.book.project.domain.Order;
import site.book.project.service.CartService;
import site.book.project.service.OrderService;

@Slf4j
@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    
    
    
    @Autowired
    private OrderService orderService;

    @Autowired
    private CartRepository cartRepository;
    
    @Test
    public void test() {
//        Assertions.assertNotNull(orderRepository);
        Assertions.assertNotNull(orderService);
        
        // cart에 있는거 하나 읽어서 order table에 저장
//        Cart c = cartRepository.findById(4).get();
//        
//        log.info("카트 4번에 있는것 {}" ,c);
        
        List<Integer> oi = new ArrayList<>();
        oi.add(1);
        oi.add(2);
//        log.info("test");
//       Integer r = orderService.create(oi);
       
        orderService.orderBtn(oi);
       
       
        
        
    }
}
