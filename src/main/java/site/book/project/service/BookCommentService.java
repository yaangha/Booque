package site.book.project.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.BookComment;
import site.book.project.domain.User;
import site.book.project.dto.BookCommentRegisterDto;
import site.book.project.repository.BookCommentRepository;
import site.book.project.repository.BookRepository;
import site.book.project.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookCommentService {

    private final BookCommentRepository bookCommentRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    
    
    // dto는 bookid, comment, username밖에 없음. 그럼comment에 넣으면 다 부족함!
    public Integer create(BookCommentRegisterDto dto) {
        log.info("한줄평 create dto {}", dto);
        
        Book book = bookRepository.findById(dto.getBookId()).get();
        User user = userRepository.findById(dto.getUserId()).get();
        
        // User name이 아닌 user번호를 입력을 해야 하나..
        
        BookComment bookComment = BookComment.builder().book(book).commentContent(dto.getCommentText())
                .user(user).build();
        
        
        
        
        
        return 0;
    }
    
    
    
    
    
}
