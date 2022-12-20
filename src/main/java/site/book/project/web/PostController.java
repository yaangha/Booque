package site.book.project.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.domain.Post;
import site.book.project.domain.User;
import site.book.project.dto.PostCreateDto;
import site.book.project.dto.PostUpdateDto;
import site.book.project.dto.UserSecurityDto;
import site.book.project.dto.PostListDto;
import site.book.project.dto.PostReadDto;
import site.book.project.service.BookService;
import site.book.project.service.PostService;
import site.book.project.service.UserService;
import site.book.project.service.ReplyService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final BookService bookService;
    private final UserService userService;
    private final ReplyService replyService;
    
    @GetMapping("/main")
    public void main() {
        log.info("main()");

    }
    
    
    @GetMapping("/list")
    public String list(@AuthenticationPrincipal UserSecurityDto userSecurityDto, String postWriter, Model model) {
        log.info("list()");
       
         
        if(userSecurityDto == null) {
            User user = userService.read(postWriter);
            Integer userId = user.getId();
            
            List<PostListDto> postList = postService.postDtoList(userId);
            
                model.addAttribute("user", user);      
                model.addAttribute("list", postList);
                
        } else if (postWriter == null) {
            
            Integer userId = userSecurityDto.getId();
            User user = userService.read(userId);
            log.info("id= {}",userId);
        
            List<PostListDto> postList = postService.postDtoList(userId);
        
                model.addAttribute("user", user);      
                model.addAttribute("list", postList);
                
        }  else if(userSecurityDto.getUsername().equals(postWriter)) {
            
            Integer userId = userSecurityDto.getId();
            User user = userService.read(userId);
            log.info("id= {}",userId);
        
            List<PostListDto> postList = postService.postDtoList(userId);
        
                model.addAttribute("user", user);      
                model.addAttribute("list", postList);
        
        } else if(!userSecurityDto.getUsername().equals(postWriter)) {
            User user = userService.read(postWriter);
            Integer userId = user.getId();
            
            List<PostListDto> postList = postService.postDtoList(userId);
            
                model.addAttribute("user", user);      
                model.addAttribute("list", postList);
        } 
      
        return "/post/list";
    }


    
    @PostMapping("/create")
    public String create(PostCreateDto dto, RedirectAttributes attrs) {
        log.info("create(dto ={})", dto);   
      
        Post entity = postService.create(dto); 
        
        attrs.addFlashAttribute("createdPostId", entity.getPostId());
        attrs.addFlashAttribute("userId", dto.getUserId());
        log.info("createdPostId: entity.gePosttId()= {}", entity.getPostId());
        return "redirect:/post/list";
    }
    
    @GetMapping({ "/detail", "/modify" })
    public void detail(Integer postId, String username ,Integer bookId, Model model) {
        log.info("detail(postId= {}, bookId={}, postWriter={})", postId, bookId, username);
        
       
        Post p = postService.read(postId);
        Book b = bookService.read(bookId);
        
        PostReadDto dto = PostReadDto.fromEntity(p);
        
        if (username == null) {
            User u = userService.read(p.getUser().getId()); 
            model.addAttribute("user", u);
        } else { 
            User u = userService.read(username);
            model.addAttribute("user", u);
        }
        
         model.addAttribute("post", p);
         model.addAttribute("book", b);
         model.addAttribute("dto", dto);
        
         
    }
   
    @PostMapping("/delete")
    public String delete(Integer postId, RedirectAttributes attrs) {
        log.info("delete(postId={})",postId);
       
        replyService.deletePostIdWithAllReply(postId);
        postService.delete(postId);
        attrs.addFlashAttribute("deletedPostId", postId);
       
        // 삭제 완료 후에는 목록 페이지로 이동(redirect) - PRG 패턴
        return "redirect:/post/list";
    }
   
    @PostMapping("/update")
    public String update(PostUpdateDto dto) {
        log.info("update(dto={})", dto);
       
        postService.update(dto);
       
        // 포스트 수정 성공 후에는 상세 페이지로 이동(redirect)
        return "redirect:/post/detail?postId=" + dto.getPostId()+"&bookId="+ dto.getBookId();
    }
   
    @GetMapping("/search")
    public String search(String type, String keyword, Model model) {
        log.info("search(type={}, keyword={})", type, keyword);
       
        List<Post> list = postService.search(type, keyword);
        model.addAttribute("list", list);
       
        return "/post/list"; // list.html 파일
    }

    
    
}
