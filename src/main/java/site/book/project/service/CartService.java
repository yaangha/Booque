package site.book.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.Cart;
import site.book.project.domain.User;
import site.book.project.dto.CartDto;
import site.book.project.repository.BookRepository;
import site.book.project.repository.CartRepository;
import site.book.project.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;    
    private final UserRepository userRepository;
    
    public List<Cart> readAll(){
        return cartRepository.findAll();
    }
    
    
    
    public List<CartDto> cartDtoList(Integer userId) {
        List<Cart> list =  cartRepository.findByUserId(userId);
        
        
        // dto를 만듦.
        // 어떤걸로? 사진, 제목, 카테고리,작가, 가격, 수량, 
        // bookDto를 만들고
        List<CartDto> dtolist = new ArrayList<>(); 
        
        
        
        // 하나씩 넣음.
        // 리스트<책DTO>를 리턴함.
        // 컨트롤러에서 
        
        for(Cart c: list) {
// dto에 cart에서 찾은 book을 넣는다
            Book book = c.getBook();
            
            CartDto dto = new CartDto(book.getBookgroup(),
                        book.getCategory(),
                        book.getBookName(),
                        book.getAuthor(), 
                        book.getPrices(), 
                        book.getBookImage(), 
                        c.getCartBookCount()
                        , c.getCartId());
            
            dtolist.add(dto);
        }
        
        return dtolist;
    }
    
    // (하은) detail 페이지 책 cart에 저장하기 -> 추가된 행 개수 리턴
    public Integer addCart(Integer userId, Integer bookId, Integer count) {
        log.info("Id(user={}, book={})", userId, bookId);
        
        // 넘길 USER, BOOK 객체 생성        
        User user = userRepository.findById(userId).get();
        Book book = bookRepository.findById(bookId).get();
        
        Cart cart = Cart.builder().cartBookCount(count).book(book).user(user).build();
        
        cartRepository.save(cart);
        
        return count;
    }
        
}
