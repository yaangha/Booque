package site.book.project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SearchController {

    @GetMapping("/search")
    public String search() {
        log.info("search()");
        
        return "/search";
    }
}
