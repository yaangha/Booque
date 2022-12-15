package site.book.project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.dto.OrderFinalInfoDto;
import site.book.project.service.OrderService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderResultController {
    
    // (하은) 주문 결과 페이지로 이동하기 위해
    
    private final OrderService orderService;
    
    @PostMapping("/orderResult")
    public String orderResult(OrderFinalInfoDto dto) {
        // dto에 저장한 값으로 DB 업데이트
        orderService.updateInfo(dto);
        
        return "book/orderResult";
    }
}
