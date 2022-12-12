package site.book.project.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.Order;
import site.book.project.domain.User;
import site.book.project.dto.OrderFromDetailDto;
import site.book.project.service.BookService;
import site.book.project.service.OrderService;
import site.book.project.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    private final UserService userService;
    private final BookService bookService;
    
    @GetMapping("/order")
    public String order(Integer orderId, Model model) {
        // log.info("userId={}, bookId={}", userId, bookId);
        // Order order = orderService.readbyOrderId(orderId);
        
        // model.addAttribute("order", order);        
        
         return "book/order";
    }

    // (하은) 디테일창에서 바로 구매하기 버튼 눌러서 한 권만 구매할 때 사용
    @PostMapping("/order")
    public String orderNow(OrderFromDetailDto dto, Model model) {
                
         Integer orderId = orderService.createFromDetail(dto);
         
         Order order = orderService.readbyOrderId(orderId);
         User user = userService.read(order.getUser().getId());
         Book book = bookService.read(order.getBook().getBookId());
                 
         model.addAttribute("order", order);
         model.addAttribute("user", user);
         model.addAttribute("book", book);

         return "book/order";
    }
    
}
