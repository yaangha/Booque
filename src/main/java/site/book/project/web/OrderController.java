package site.book.project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

}
