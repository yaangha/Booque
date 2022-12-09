package site.book.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.BookWish;
import site.book.project.dto.BookWishDto;
import site.book.project.repository.BookWishRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookWishService {

    private final BookWishRepository bookWishRepository;
    
    // (하은) 사용자 찜한 리스트 찾기
    // 1) 각 사용자에 저장된 찜한 책 리스트 뽑기
    // 2) 해당 책 id로 책 정보 뽑기
    public List<BookWishDto> searchWishList(Integer id) {
        
        List<BookWish> wishList = bookWishRepository.findAllByUserId(id);
        
        List<BookWishDto> wishBookInfo = new ArrayList<>();
        
        for (BookWish bw : wishList) {
            BookWishDto dto = new BookWishDto(
                    bw.getBook().getBookId(), bw.getBook().getAuthor(), bw.getBook().getBookName(), 
                    bw.getBook().getBookImage(), bw.getBook().getPrices(), 
                    bw.getBook().getCategory(), bw.getBook().getBookgroup());
            wishBookInfo.add(dto);
        }

        return wishBookInfo;
    }
    
}
