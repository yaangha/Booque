package site.book.project.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Cart;
import site.book.project.domain.User;
import site.book.project.dto.CartDto;
import site.book.project.repository.CartRepository;
import site.book.project.service.CartService;
import site.book.project.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {
// 유저 한명당 하나의 카트 페이지 존재함
// queryString을 유저 번호로 넘김
    
    private final UserService userService;
    private final CartService cartService;
    
    
    // Principal 객체가 있군 user객체 안뜰때 사용
    @GetMapping("/cart")
    public String cart(Integer id, Model model ){
        log.info("사용자 번호 {}", id);
        
        User user = userService.read(id); 
        List<CartDto> cartList = cartService.cartDtoList(id);
        
        // 생성된 CartDTO를 받고 
        // 
        
        
        model.addAttribute("user", user);
        model.addAttribute("cartList", cartList);
        
        
        return "book/cart";
        
    }
    
    
}
