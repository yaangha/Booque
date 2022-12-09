package site.book.project.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Cart;
import site.book.project.domain.Order;
import site.book.project.repository.CartRepository;
import site.book.project.repository.OrderRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
// 장바구니 내역에 있는걸 가져와서 결제 창에 보일 수 있도록
// 결제확인(완료)이 되면 장바구니(CART)테이블 동일한 값(list로 받은 값) 삭제 TODO
// 결제 취소시 다시 결제 테이블(ORDER) 삭제  TODO
    
    
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    
    
    public List<Order> readAll(){
        
        return orderRepository.findAll();
    }
    
    // 최신순
    public List<Order> readAllDesc(){
        log.info("order 서비스~!");
        return orderRepository.findByOrderByOrderIdDesc();
    }
    
    public Integer create(List<Integer> cartId) { // 받아야 하는게 뭐죠? 카트 번호
        // cart가 여러개 1,2,3, ... 
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")); // ex) 20221209
        
        List<Order> orderList = new ArrayList<>();
        
        for(Integer i : cartId) {
            Cart c = cartRepository.findById(i).get();
            
            Integer orderNo = Integer.parseInt(date+c.getUser().getId());
            
            
            Order o = Order.builder().user(c.getUser())
                    .book(c.getBook())
                    .orderBookCount(c.getCartBookCount())
                    .orderNo(orderNo).build();
            
            orderRepository.save(o);
            
            orderList.add(o);
        }
        
        
        
        
        
        
        return orderList.size();
    }
    
    
}
