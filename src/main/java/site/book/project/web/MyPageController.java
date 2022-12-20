package site.book.project.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.dto.UserSecurityDto;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MyPageController {
    
    // (하은) 마이페이지 연결
    @GetMapping("/myPage")
    public String myPage(@AuthenticationPrincipal UserSecurityDto userSecurityDto) {
        
        return "/book/myPage";
    }
    
}
