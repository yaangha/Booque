package site.book.project.web;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Order;
import site.book.project.domain.User;
import site.book.project.dto.OrderNoList;
import site.book.project.dto.UserModifyDto;
import site.book.project.dto.UserSecurityDto;
import site.book.project.repository.UserRepository;
import site.book.project.service.OrderService;
import site.book.project.service.UserService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MyPageController {
    
    private final UserRepository userRepository;
    private final OrderService orderService;
    private final UserService userService;
    
    
    // (하은) 마이페이지 연결
    @GetMapping("/myPage")
    public String myPage(@AuthenticationPrincipal UserSecurityDto u, Model model) {
        
        User user = userRepository.findById(u.getId()).get();
        
        // 주문내역 확인 리스트로 가져옴. 날짜별로  최근순 
        List<Order> orderList = orderService.readByUserId(user.getId());
        List<OrderNoList> noList = orderService.listOrderNo(user.getId());
        // 주문 내역별 배송지 확인, bookid리스트, 
        
        model.addAttribute("orderList", orderList);
        model.addAttribute("user", user);
        
        return "/book/myPage";
    }
    
    
    // (은정)
    @PostMapping("/myPage/modify")
    public String modify(@AuthenticationPrincipal UserSecurityDto u,
                      UserModifyDto userModifyDto) {
        // 중복검사는 ajax로 해야함..
        Integer result = userService.modify(userModifyDto, u);
        
        return "redirect:/myPage";
    }
    
    // 못함
    @PostMapping("/myPage/file")
    public String filemodify(@AuthenticationPrincipal UserSecurityDto  userSecurityDto,
                            @RequestParam("filePath") MultipartFile file) throws IllegalStateException, IOException {
//        log.info("create(dto={})-post방식",dto);
//        FreeSharePost entity = freeSharePostService.create(dto, file);
//        attrs.addFlashAttribute("createdId", entity.getId());
//        
//        public FreeSharePost create(FreeSharePostCreateDto dto, MultipartFile file) throws Exception {
//            log.info("create(dto={})",dto);
//            FreeSharePost freeSharePost=dto.toEntity();
//            String projectPath=System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files";
//            
//            log.info(projectPath);
//            UUID uuid=UUID.randomUUID();
//            String fileName = uuid+"_"+file.getOriginalFilename();
//            File saveFile=new File(projectPath, fileName);
//            file.transferTo(saveFile);
//            freeSharePost.setFileName(fileName);        //생성한 파일이름을 저장해줌.
//            System.out.println(fileName);
//            System.out.println(freeSharePost.toString());
//            freeSharePost.setFilePath("/files/" + fileName);
//            
//            return freeSharePostRepository.save(freeSharePost);
//        }
        log.info("사진 바꾸는 컨트롤러~~~~```");
        log.info("사진, 유저 ~~~~~ {}" , file);
        userService.modifyUserImage(userSecurityDto.getId(), file);
        
        
        
        
        return "redirect:/";
    }
    
    
}
