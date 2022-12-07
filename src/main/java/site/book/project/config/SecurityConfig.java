package site.book.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         // Spring Security는 GET 방식을 제외한 POST/PUT/DELETE 요청에서 
//         // CERF 토큰을 요구함.
//         // POST/PUT/DELETE 요청에서 CSRF 토큰을 서버로 전송하지 않으면 403(forbidden:권한 없음) 에러가 발생.
//         // 기능 구현을 간단히하기 위해서 Spring Security의 CFRS 기능을 비활성화. 
//        http.csrf().disable();
//        
//        return http.build();
//    }
}
