package site.book.project.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.dto.ReplyReadDto;
import site.book.project.dto.ReplyRegisterDto;
import site.book.project.service.ReplyService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class PostReplyController {

    private final ReplyService replyService;
    
    // 댓글 전체 리스트
    @GetMapping("/all/{postId}")
    public ResponseEntity<List<ReplyReadDto>> readAllReplies(@PathVariable Integer postId){
        log.info("readAllReplies(postId={})", postId);
        
        List<ReplyReadDto> list = replyService.readReplies(postId);
        log.info("# of list = ", list.size());
        
        return ResponseEntity.ok(list);
    }
    
    // 댓글 작성
    @PostMapping
    public ResponseEntity<Integer> registerReply(@RequestBody ReplyRegisterDto dto) {
        log.info("registerReply()");
        
        Integer replyId = replyService.create(dto);
        
        return ResponseEntity.ok(replyId);
    }
    
    
}
