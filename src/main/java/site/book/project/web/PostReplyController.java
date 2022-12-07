package site.book.project.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.dto.ReplyReadDto;
import site.book.project.dto.ReplyRegisterDto;
import site.book.project.dto.ReplyUpdateDto;
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

        List<ReplyReadDto> list = replyService.readReplies(13);
        log.info("# of list = {}", list.size());

        return ResponseEntity.ok(list);
    }

    // 댓글 작성
    @PostMapping
    public ResponseEntity<Integer> registerReply(@RequestBody ReplyRegisterDto dto) {
        log.info("registerReply()");

        Integer replyWriter = replyService.create(dto);

        return ResponseEntity.ok(replyWriter);
    }
    
    // 댓글 수정/삭제 모달창에 가져오기
    @GetMapping("/{replyId}")
    public ResponseEntity<ReplyReadDto> getReply(@PathVariable Integer replyId) {
        log.info("getReply(replyId={})", replyId);
        
        ReplyReadDto dto = replyService.readReply(replyId);
        
        return ResponseEntity.ok(dto);
    }
    
    // 댓글 삭제
    @DeleteMapping("/{replyWriter}")
    public ResponseEntity<Integer> deleteReply(@PathVariable Integer replyWriter) {
        log.info("deleteReply(replyWriter={})", replyWriter);
        
        Integer result = replyService.delete(replyWriter);
        
        return ResponseEntity.ok(result);
    }
    
    // 댓글 수정
    @PutMapping("/{replyWriter}")
    public ResponseEntity<Integer> updateReply(
            @PathVariable String replyWriter,
            @RequestBody ReplyUpdateDto dto) {
        log.info("updateReply(replyWriter={}, dto={})", replyWriter, dto);
        
        dto.setReplyWriter(replyWriter); 
        Integer result = replyService.update(dto);
        
        return ResponseEntity.ok(result);
    }
}