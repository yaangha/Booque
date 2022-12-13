package site.book.project.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.Cart;
import site.book.project.domain.Order;
import site.book.project.domain.User;
import site.book.project.dto.OrderFinalInfoDto;
import site.book.project.dto.OrderFromDetailDto;
import site.book.project.repository.BookRepository;
import site.book.project.repository.CartRepository;
import site.book.project.repository.OrderRepository;
import site.book.project.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
// 장바구니 내역에 있는걸 가져와서 결제 창에 보일 수 있도록
// 결제확인(완료)이 되면 장바구니(CART)테이블 동일한 값(list로 받은 값) 삭제 TODO
// 결제 취소시 다시 결제 테이블(ORDER) 삭제  TODO
// !!!!! 테스트용으론 orderDATE 넣어놈!!!! 
    
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    
    public List<Order> readAll(){
        return orderRepository.findAll();
    }
    
    // 최신순
    public List<Order> readAllDesc(){
        log.info("order 서비스~!");
        return orderRepository.findByOrderByOrderIdDesc();
    }
    
    // 결제 완료 버튼을 누르면 장바구니데이터는 사라지고(cart에서 delete를 만들면 됨! 여기서 필요 없음)
    // 결제 취소 버튼을 누르면 (creat()) 결제데이터 사라짐.
    public void orderBtn(List<Integer> cartId) {
    	for(Integer i : cartId) {
    		log.info("장바구니에 있는 데이터 삭제");
    		cartRepository.deleteById(i);
    	}
    	
    }
    
    
    
    
    
    // 장바구니(cart)에서 받은 데이터로 
    /**
     * 장바구니-> 결제창 
     * 결제 버튼 누름과 동시에 실행될 메서드
     * 총 금액 필요 함?? TODO
     * @param cartId 결제할 책 정보를 가지고 있는 PK
     * @return 뭘 리턴해야 할지 모르겠음. 여러줄의 객체가 생성되는데..!
     */
    public Integer create(List<Integer> cartId) { // user를 받을 필요는 없겠지?
        // cart가 여러개 1,2,3, ... 
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")); // ex) 20221209
        
        List<Order> orderList = new ArrayList<>();
        
        for(Integer i : cartId) {
            Cart c = cartRepository.findById(i).get();
            
            Integer orderNo = Integer.parseInt(date+c.getUser().getId());
            
            
            Order o = Order.builder().user(c.getUser())
                    .book(c.getBook())
                    .orderBookCount(c.getCartBookCount())
                    .orderDate(LocalDateTime.now())
                    .orderNo(orderNo).build();
            
            orderRepository.save(o);
            
            orderList.add(o);
        }
        
        return orderList.size();
    }

    // (하은) 디테일 페이지에서 바로 구매하는 페이지로 넘어할 때 사용
    public Integer createFromDetail(OrderFromDetailDto dto) {
        
        Integer total = dto.getCount() * dto.getPrice(); // 수량 X 가격
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")); // ex) 20221209
        Integer orderNo = Integer.parseInt(date + dto.getUserId());
        
        User user = userRepository.findById(dto.getUserId()).get();
        Book book = bookRepository.findById(dto.getId()).get();
        
        Order order = Order.builder().orderNo(orderNo).user(user).book(book)
                .orderDate(LocalDateTime.now()).orderBookCount(dto.getCount()).total(total).build();
        
        Order orderResult = orderRepository.save(order);
        
        return orderResult.getOrderId();
    }
    
    // (하은) 바로 구매하는 책에 대한 order 테이블 데이터 불러오기
    public Order readbyOrderId(Integer orderId) {
        
        Order order = orderRepository.findById(orderId).get();
        
        return order;
    }

    // (하은) orderResult로 넘어갈 때 최종 주문&배송정보 order DB에 업데이트하기 위해
    public void updateInfo(OrderFinalInfoDto dto) {
        // Order DB에 업데이트할 데이터 -> 배송정보(3개), 메시지, 결제방식
        log.info("하은 주문번호={}", dto.getOrderId());
        Order order = orderRepository.findById(dto.getOrderId()).get();
        order.update(dto.getPostcode(), dto.getAddress(), dto.getDetailAddress(), dto.getPayOption(), dto.getMessage());
        orderRepository.save(order);
    
    }
    
    
    
    
    
    
}
