package site.book.project.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.Cart;
import site.book.project.domain.User;
import site.book.project.dto.OrderFromDetailDto;
import site.book.project.dto.OrderFinalInfoDto;
import site.book.project.dto.OrderFromCartDto;
import site.book.project.service.BookService;
import site.book.project.service.CartService;
import site.book.project.service.OrderService;
import site.book.project.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;
    private final BookService bookService;
    
    // (하은) 카트에서 결제하기 버튼 눌렀을 때 사용
    @PostMapping("/order")
    public String order(Integer[] cartId, Model model) { 
          Long orderNo = orderService.create(cartId);
          
          log.info("하은 cartId = {}", cartId[0]);
          
          List<OrderFromCartDto> order = new ArrayList<>();
          
          Cart cartFindUser = cartService.read(cartId[0]);
          User user = userService.read(cartFindUser.getUser().getId());
                    
          for (Integer i : cartId) {
              Cart cart =  cartService.read(i);
              User userForId = userService.read(cart.getUser().getId());
              Book book = bookService.read(cart.getBook().getBookId());
              
              OrderFromCartDto dto = OrderFromCartDto.builder().userId(userForId.getId()).id(book.getBookId()).cartId(cart.getCartId())
                      .prices(book.getPrices()).count(cart.getCartBookCount()).bookName(book.getBookName()).publisher(book.getPublisher())
                      .bookImage(book.getBookImage()).author(book.getAuthor()).category(book.getCategory()).bookgroup(book.getBookgroup())
                      .build();
              
              order.add(dto);
              
          }
          
          model.addAttribute("order", order);
          model.addAttribute("user", user);
          model.addAttribute("orderNo", orderNo);
          
           return "book/order";
      }
    
    // (하은) 디테일창에서 바로 구매하기 버튼 눌러서 한 권만 구매할 때 사용
    @PostMapping("/orderFromDetail")
    public String orderNow(OrderFromDetailDto dto, Model model) {
                
         Long orderNo = orderService.createFromDetail(dto);
         
         List<OrderFromCartDto> order = orderService.readByOrderNo(orderNo);
                 
         User user = userService.read(order.get(0).getUserId());
         
         model.addAttribute("order", order);
         model.addAttribute("user", user);
         model.addAttribute("orderNo", orderNo);

         return "book/order";
    }
    
}
