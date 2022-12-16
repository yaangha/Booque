package site.book.project.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.dto.OrderFinalInfoDto;
import site.book.project.dto.OrderFromCartDto;
import site.book.project.repository.OrderRepository;
import site.book.project.service.OrderService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderResultController {
    
    // (하은) 주문 결과 페이지로 이동하기 위해
    private final OrderService orderService;
    
    @PostMapping("/orderResult")
    public String orderResult(Integer[] cartId, OrderFinalInfoDto dto) {
        // dto에 저장한 값으로 DB 업데이트
        orderService.updateInfo(cartId, dto);
        
        // 주문완료시 장바구니 내역은 삭제
        orderService.deleteCart(cartId);
        
        // 주문내역 보여주기 위해서.. 주문번호에 맞는 책 찾고, 유저 찾아서 넘기기
        // orderFromCartDto 사용! 거기에 책 정보 다 들어있음.
        // List<OrderFromCartDto> cartDto = orderService.readByOrderNo(dto.getOrderNo());
        
        
        
        return "book/orderResult";
    }
    
    
    // (하은) 결제 페이지에서 결제취소 버튼 누르면 order DB 삭제하고 메인 페이지(?)로 이동
    @PostMapping("/orderCancel")
    public String orderCancel(OrderFinalInfoDto dto) {
        
        orderService.deleteInOrder(dto.getOrderNo());
        
        return "redirect:/";
    }
    
}
