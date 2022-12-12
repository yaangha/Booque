package site.book.project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.dto.OrderFromDetailDto;
import site.book.project.service.OrderService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    
    @GetMapping("/order")
    public String order() {  // db 전송 우째 할지 고민
        //
        
        return "/book/order";
    }

    // (하은) 디테일창에서 바로 구매하기 버튼 눌러서 한 권만 구매할 때 사용
    @PostMapping("/order/now")
    public String orderNow(OrderFromDetailDto dto) {
         orderService.createFromDetail(dto);
        
        return "/book/order";
    }
    
}
