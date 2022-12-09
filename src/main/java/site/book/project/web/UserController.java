package site.book.project.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.dto.UserRegisterDto;
import site.book.project.service.UserService;

@Slf4j
@Controller 
@RequiredArgsConstructor
@RequestMapping("/user") 
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/signup") 
    public void signUp() {
        log.info("signUp() GET");
    } 
    
    @GetMapping("/checkid")
    @ResponseBody 
    public ResponseEntity<String> checkUsername(String username) {
        log.info("checkUsername (username={})", username);
        
        String result = userService.checkUsername(username);
        
        return ResponseEntity.ok(result);
        
    } // Rest방식, 리턴값이 Body에 간다 => HTML을 만들지 않을거다!
    
    @PostMapping("/signup")
    public String signUp(UserRegisterDto dto) {
        log.info("signUp(dto = {}) POST", dto);
        
        userService.registerUser(dto);
       
        
        return "redirect:/main"; // 회원가입 성공 후 이동(redirect)
    }

}
