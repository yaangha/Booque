package site.book.project.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.service.ReplyService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class PostReplyController {

    private ReplyService replyService;
    
    @GetMapping
    public void home() {
        
    }
}
