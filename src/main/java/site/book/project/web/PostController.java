package site.book.project.web;

import java.util.ArrayList;
import java.util.List;

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
import site.book.project.dto.PostCreateDto;
import site.book.project.dto.PostUpdateDto;
import site.book.project.dto.PostListDto;
import site.book.project.service.BookService;
import site.book.project.service.PostService;
import site.book.project.service.ReplyService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final BookService bookService;
    private final ReplyService replyService;
    
    @GetMapping("/main")
    public void main() {
        log.info("main()");

    }
    
    
    @GetMapping("/list")
    public String list(Model model) {
        log.info("list()");
        
        List<Post> postList = postService.read();
        List<Book> bookList = bookService.read();
        List<PostListDto> list = new ArrayList<>();
        
        for (Post p : postList) {
            PostListDto dto = null;
            for (Book b : bookList) {
                if(b.getBookId() == p.getBookId()) {
                 dto = PostListDto.builder().postId(p.getPostId()).bookId(p.getBookId()).title(p.getTitle()).bookImage(b.getBookImage()).modifiedTime(p.getModifiedTime()).build();
                 list.add(dto);
                }
            }
        }
                
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
    public void detail(Integer postId, Integer bookId, Model model) {
        log.info("detail(postId= {}, bookId={})", postId, bookId);
        
        Post post = postService.read(postId);
        Book book = bookService.read(bookId);
      
        
        model.addAttribute("book", book);
        model.addAttribute("post", post);
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
