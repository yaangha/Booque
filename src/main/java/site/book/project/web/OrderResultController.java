package site.book.project.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Order;
import site.book.project.domain.User;
import site.book.project.dto.OrderFinalInfoDto;
import site.book.project.dto.OrderFromCartDto;
import site.book.project.dto.UserSecurityDto;
import site.book.project.repository.OrderRepository;
import site.book.project.service.OrderService;
import site.book.project.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderResultController {
    
    // (하은) 주문 결과 페이지로 이동하기 위해
    private final OrderService orderService;
    private final UserService userService;
    
    // 무통장입금, 카카오페이 결과 페이지 다르게 보일 수 있게 수정! 하면 위에 코드 삭제
    @PostMapping("/orderResult")
    public String orderResult(@AuthenticationPrincipal UserSecurityDto userSecurityDto, Integer[] cartId, OrderFinalInfoDto dto, Model model) {
        // dto에 저장한 값으로 DB 업데이트
        
        if (cartId.length == 0) {
            orderService.updateInfo(dto.getOrderNo(), dto);
            log.info("하은 orderNo = {}", dto.getOrderNo());

        } else {
            orderService.updateInfo(cartId, dto);
        }
        
        // 주문완료시 장바구니 내역은 삭제
        orderService.deleteCart(cartId);
        
        // 주문내역 보여주기 위해서.. 주문번호에 맞는 책 찾고, 유저 찾아서 넘기기
        // orderFromCartDto 사용! 거기에 책 정보 다 들어있음.
        Integer userId = userSecurityDto.getId();
        log.info("하은 userId={}", userId);
        User user = userService.read(userId);
        List<OrderFromCartDto> orderInfo = orderService.readByOrderNo(dto.getOrderNo());
        Long orderNo = dto.getOrderNo();
        
        Integer total = 0;
        
        for (int a = 0; a < orderInfo.size(); a++) {
            total += orderInfo.get(a).getPrices() * orderInfo.get(a).getCount();
        }
        
        model.addAttribute("user", user);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("orderNo", orderNo);
        model.addAttribute("total", total);
        model.addAttribute("order", dto);
        
        if (dto.getPayOption().equals("무통장입금")) {
            return "book/orderCash";
        } else {
            return "book/orderKaKao";
        }
        
    }
    
    // (하은) 결제 페이지에서 결제취소 버튼 누르면 order DB 삭제하고 메인 페이지(?)로 이동
    @PostMapping("/orderCancel")
    public String orderCancel(OrderFinalInfoDto dto) {
        
        orderService.deleteInOrder(dto.getOrderNo());
        
        return "redirect:/";
    }
    
}
