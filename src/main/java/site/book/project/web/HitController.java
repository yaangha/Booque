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
    // bookDetail 관련 조회수
    @GetMapping("/viewCount")
    private void viewCountUp(Integer bookId, HttpServletRequest request, HttpServletResponse response) {
        log.info("viewCountUp(bookId={})", bookId);
        
        Cookie viewCookie = null; // 쿠키값 정의
        Cookie[] cookies = request.getCookies(); // 클라이언트에서 보낸 데이터에서 쿠키 값을 가져옴
        if (cookies != null) { // 쿠키들이 있다면,
            for (Cookie cookie : cookies) { // 있는 쿠키들 중에 bookDetail 쿠키가 있으면 viewCookie를 쿠키로 저장.
                if (cookie.getName().equals("bookDetailViewCount")) {
                    viewCookie = cookie;
                }
            }
        }
        
        // 만들어진 쿠키가 없을 때
        if (viewCookie != null) { // view 쿠키가 있다면, 
            if (!viewCookie.getValue().contains("[" + bookId.toString() + "]")) {
                bookHitsService.viewCountUp(bookId);
                viewCookie.setValue(viewCookie.getValue() + "_[" + bookId + "]");
                viewCookie.setPath("/");
                viewCookie.setMaxAge(60 * 1);
                response.addCookie(viewCookie);
            }
        } else { // view 쿠키가 없을 경우 쿠키를 만들고 조회수 1을 증가시켜주기
            bookHitsService.viewCountUp(bookId);
            Cookie newCookie = new Cookie("bookDetailViewCount","[" + bookId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 1);
            response.addCookie(newCookie);
        }
//        for (Cookie cookie : cookies) {
//            log.info("쿠키목록={}",cookie.getValue()
//        }
        
    }
    
    // postDetail 관련 조회수
    @GetMapping("/postHitCount")
    private void postHitsUp(Integer postId, HttpServletRequest request, HttpServletResponse response) {
        log.info("postHitsUp(postId={})", postId);
        
        Cookie hitCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postDetailHitCount")) {
                    hitCookie = cookie;
                }
            }
        }
        if (hitCookie != null) {
            if (!hitCookie.getValue().contains("[" + postId.toString() + "]")) {
                postHitsService.postHitsUp(postId);
                hitCookie.setValue(hitCookie.getValue() + "_[" + postId + "]");
                hitCookie.setPath("/");
                hitCookie.setMaxAge(60 * 1);
                response.addCookie(hitCookie);
            }
        } else {
            postHitsService.postHitsUp(postId);
            Cookie newCookie = new Cookie("postDetailHitCount","[" + postId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 1);
            response.addCookie(newCookie);
        }
        
    }
    
    
}
