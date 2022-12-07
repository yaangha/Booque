package site.book.project.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// 책 한줄평 REST 컨트롤러( AJax)
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.dto.BookCommentReadDto;
import site.book.project.dto.BookCommentRegisterDto;
import site.book.project.service.BookCommentService;

@Slf4j
@RequiredArgsConstructor
@RestController  // response.data
public class BookCommentRestController {
    
    private final BookCommentService bookCommentService;
    
    @PostMapping("/api/comment") // 한줄평 insert
    public ResponseEntity<Integer> registerComment(@RequestBody BookCommentRegisterDto dto){
        log.info("한줄평 dto ={}",dto);
        
        Integer commentId = bookCommentService.create(dto);
        
        
        return ResponseEntity.ok(commentId);
    }
    
    @GetMapping("/api/comment/all/{bookId}")
    public ResponseEntity<List<BookCommentReadDto>> readAllCommentDesc(@PathVariable Integer bookId) {
        log.info("comment여기가 바로! bookREST!!! bookId= {}", bookId);
        
        List<BookCommentReadDto> list = bookCommentService.readComment(bookId);
        
        return ResponseEntity.ok(list);
        
    }
    
    // 주소값 바꿔서 하기!! 삭제도 하기!
//    @GetMapping("/api/comment/all/{bookId}")
//    public ResponseEntity<List<BookCommentReadDto>> readAllComment(@PathVariable Integer bookId) {
//        log.info("comment여기가 바로! bookREST!!! bookId= {}", bookId);
//        
//        List<BookCommentReadDto> list = bookCommentService.readComment(bookId);
//        
//        return ResponseEntity.ok(list);
//        
//    }
    
    
    
    
    
}
