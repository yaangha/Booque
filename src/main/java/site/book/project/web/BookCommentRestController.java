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
import site.book.project.domain.Post;
import site.book.project.dto.BookCommentReadDto;
import site.book.project.dto.BookCommentRegisterDto;
import site.book.project.dto.PostReadDto;
import site.book.project.service.BookCommentService;
import site.book.project.service.PostService;

@Slf4j
@RequiredArgsConstructor
@RestController  // response.data
public class BookCommentRestController {
    
    private final BookCommentService bookCommentService;
    private final PostService postService;
    
    
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
    @GetMapping("/api/comment/allOrderLike/{bookId}")
    public ResponseEntity<List<BookCommentReadDto>> readAllComment(@PathVariable Integer bookId) {
        log.info("라이크 순서대로 할거임!!!!! bookId= {}", bookId);
        
        List<BookCommentReadDto> list = bookCommentService.readLikeComment(bookId);
        log.info("라이크 순서대로 리스트 {}", list);
        
        
        return ResponseEntity.ok(list);
        
    }
    
    
    
    // postRest 사용할 예정
    // 먼저 repositoy에서 query랑 문장 만들고 실행 되는지 확인  완.
    // service로 옮겨서 실행 완성.되면
    // ajax(post.js)만들고 컨트롤러(여기) get방식으로 받음 
    // DTO를 만들어야 함... 
    // TODO
    @GetMapping("/api/post/all/{bookId}")
    public ResponseEntity<List<PostReadDto>> readPostDesc(@PathVariable Integer bookId){
        // bookid 받고 거기에 있는 post를 최신순으로
        List<PostReadDto> list = postService.findDesc(bookId);
        
        log.info(" 포스트 최신 순{}",list);
        
        
        return ResponseEntity.ok(list);
    }
    
    
    
    
    
    
    
    
    
}
