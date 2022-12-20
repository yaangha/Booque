package site.book.project.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.service.BookHitsService;
import site.book.project.service.PostHitsService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HitController {
    
    private final BookHitsService bookHitsService;
    private final PostHitsService postHitsService;
    
    
    // 쿠키써서 조회수 어뷰징 방지
    // 쿠키 재적용 시간은 setMaxAge에서 시간 조절 가능, 일단 24시간으로 설정
    // TODO: login ID와 비회원에 따라 구분하여 적용하는 것은 아직 
    @GetMapping("/viewCount")
    private void viewCountUp(Integer bookId, HttpServletRequest request, HttpServletResponse response) {
        log.info("viewCountUp(bookId={})", bookId);
        
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("bookDetailViewCount")) {
                    oldCookie = cookie;
                }
            }
        }
        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + bookId.toString() + "]")) {
                bookHitsService.viewCountUp(bookId);
                oldCookie.setValue(oldCookie.getValue() + "_[" + bookId + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            bookHitsService.viewCountUp(bookId);
            Cookie newCookie = new Cookie("bookDetailViewCount","[" + bookId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
        
    }
    
    @GetMapping("/postHitCount")
    private void postHitsUp(Integer postId, HttpServletRequest request, HttpServletResponse response) {
        log.info("viewCountUp(postId={})", postId);
        
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("bookDetailViewCount")) {
                    oldCookie = cookie;
                }
            }
        }
        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + postId.toString() + "]")) {
                postHitsService.postHitsUp(postId);
                oldCookie.setValue(oldCookie.getValue() + "_[" + postId + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            postHitsService.postHitsUp(postId);
            Cookie newCookie = new Cookie("bookDetailViewCount","[" + postId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
        
    }
    
    
}
