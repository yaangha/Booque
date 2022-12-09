package site.book.project.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Post;
import site.book.project.dto.PostCreateDto;
import site.book.project.dto.PostUpdateDto;
import site.book.project.service.PostService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    
    @GetMapping("/main")
    public void main() {
        log.info("main()");

    }
    
    
    @GetMapping("/list")
    public String list(Model model) {
        log.info("list()");
        
        List<Post> list = postService.read();
        model.addAttribute("list", list);
        
        return "/post/list";
    }

    
    @PostMapping("/create")
    public String create(PostCreateDto dto, RedirectAttributes attrs) {
        log.info("create(dto ={})", dto);
        
        Post entity = postService.create(dto);
        
        attrs.addFlashAttribute("createdPostId", entity.getPostId());
        log.info("createdPostId: entity.gePosttId()= {}", entity.getPostId());
        return "redirect:/post/list";
    }
    
    @GetMapping({ "/detail", "/modify" })
    public void datail(Integer postId, Model model) {
        log.info("detail(postId= {})", postId);
       
        Post post = postService.read(postId);
        model.addAttribute("post", post);
    }
   
    @PostMapping("/delete")
    public String delete(Integer postId, RedirectAttributes attrs) {
        log.info("delete(postId={})",postId);
       
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
        return "redirect:/post/detail?postId=" + dto.getPostId();
    }
   
    @GetMapping("/search")
    public String search(String type, String keyword, Model model) {
        log.info("search(type={}, keyword={})", type, keyword);
       
        List<Post> list = postService.search(type, keyword);
        model.addAttribute("list", list);
       
        return "/post/list"; // list.html 파일
    }

    
    
}
