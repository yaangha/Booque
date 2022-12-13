package site.book.project.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.dto.CartDto;
import site.book.project.service.CartService;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CartRestController {

    private final CartService cartService;
    
    @GetMapping("api/cart/all/{userId}")
    public ResponseEntity<List<CartDto>> read(@PathVariable Integer userId){
        
        return ResponseEntity.ok(cartService.cartDtoList(userId));
    }

    @PostMapping("api/cartid")
    public ResponseEntity<List<CartDto>> cartListll(@RequestBody ArrayList<Integer> ckList){

        log.info("  ~~~~~~~~~~` 여기는? {}",ckList);
        cartService.deleteCart(ckList);
        // 유저 번호 그냥 얻자...  가장 끝번호에 추가하면 되잖아
        // 유저 번호 
        List<CartDto> cartDtoList =  cartService.cartDtoList(ckList.get(ckList.size()-1));
        log.info("유저번호가 제대로 넘어 갔나 ? {} , {}",ckList.get(ckList.size()-1) , cartDtoList );
        
        log.info("여기~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~````");
        return ResponseEntity.ok(cartDtoList);
    }

    
    
    
    
    
}
