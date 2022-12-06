package site.book.project.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// 책 한줄평 REST 컨트롤러( AJax)
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.dto.BookCommentRegisterDto;
import site.book.project.service.BookCommentService;

@Slf4j
@RequiredArgsConstructor
@RestController  // response.data
public class BookCommentRestController {
    
    private final BookCommentService commentService;
    
    @PostMapping("/api/comment")
    public ResponseEntity<Integer> registerComment(@RequestBody BookCommentRegisterDto dto){
        log.info("한줄평 dto ={}",dto);
        
        
        return null;
    }
    
    
    
    
}
