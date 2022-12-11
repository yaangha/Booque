package site.book.project.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.User;
import site.book.project.dto.BookWishDto;
import site.book.project.dto.CartAddDto;
import site.book.project.dto.CartDto;
import site.book.project.service.BookWishService;
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
    private final BookWishService bookWishService;

    // Principal 객체가 있군 user객체 안뜰때 사용
    @GetMapping("/cart")
    public String cart(Integer id, Model model ){
        log.info("사용자 번호 {}", id);
        
        User user = userService.read(id); 
        List<CartDto> cartList = cartService.cartDtoList(id);
        
        // 생성된 CartDTO를 받고
        
        // (하은) detail 창의 bookid를 받아서 cart DB에 update
        
        
        
        // (하은) userId로 조건에 맞는 행 찾기 -> bookId로 book 정보 찾기        
        List<BookWishDto> wishBookInfo = bookWishService.searchWishList(id);
        
        model.addAttribute("wishBookInfo", wishBookInfo);
        model.addAttribute("user", user);
        model.addAttribute("cartList", cartList);

        return "book/cart";
        
    }
    
    // detail 페이지에서 cart로 넘어갈 때 사용
    @PostMapping("/cart/add")
    public String addCart(CartAddDto dto) {
        log.info("사용자 번호={}", dto.getId());
        
        if (cartService.checkUser(dto.getUserId(), dto.getId()) == 1) { // 사용자 없으면 create
            cartService.addCart(dto.getUserId(), dto.getId(), dto.getCount());
        } else { // 사용자 있으면 update
            Integer afterCount = cartService.updateCount(dto.getUserId(), dto.getId(), dto.getCount());
            log.info("변경 수량={}", afterCount);
        }
        
        return "redirect:/cart?id=" + dto.getUserId();
    }

    // 장바구니에 넣고 쇼핑 계속하기 버튼 눌렀을 때 사용
    @PostMapping("/cart/onlyAdd")
    public String onlyAddCart(CartAddDto dto) {
        log.info("사용자 번호={}", dto.getId());
        
        if (cartService.checkUser(dto.getUserId(), dto.getId()) == 1) { // 사용자 없으면 create
            cartService.addCart(dto.getUserId(), dto.getId(), dto.getCount());
        } else { // 사용자 있으면 update
            Integer afterCount = cartService.updateCount(dto.getUserId(), dto.getId(), dto.getCount());
            log.info("변경 수량={}", afterCount);
        }
        
        return "redirect:/detail?id=" + dto.getId();
    }    

    @PostMapping("/cart/delete")
    public String delete(Integer cartId, Integer userId , String hi) {
    	log.info("이게 맞나뇨,,, cartId {}, user Id {} ,test {}", cartId, userId,hi);
    	
    	return "redirect:/book/cart?id="+userId;
    }
    
}
