package site.book.project.web;

import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.service.ReplyService;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class PostReplyController {

    private ReplyService replyService;
    
    public void home() {
        
    }
}
