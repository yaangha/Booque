package site.book.project.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.service.OrderService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    
    
    @PostMapping("/order")
    public String order(Integer[] cartId, Model model) { 
        
        orderService.create(cartId);
        // TODO 결제창에서 보여줄 데이터 DTO 만들어서 넘길 예정

        
       // model.addAttribute("주무", model);
        
         
        return "/book/order";
    }

}
