package site.book.project.service;

import java.util.Optional;

import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.User;
import site.book.project.dto.UserRegisterDto;
import site.book.project.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    
    
    private final UserRepository userRepository;
    
    public String checkUsername(String username) {
        log.info("checkUsername(username = {})", username);
        
        Optional<User> result = userRepository.findByUsername(username); // Optional return.
        if (result.isPresent()) { // isEmpty는 11버전부터, 우리가 설정한건 8버전. 일치하는 username이 있는 경우.
                return "nok"; // 회원 가입에서 사용할 수 없는 아이디
            } else { // 일치하는 username이 없는 경우.
                return "ok"; // 회원 가입에서 사용할 수 있는 아이디
            }
    }

    public User registerMember(UserRegisterDto dto) {
        log.info("registerMember(dto = {})", dto);
        
        // 로그인 비밀번호를 암호화한 후 DB에 insert
        User entity = userRepository.save(dto.toEntity());
        log.info("entity = {}", entity);
        
        return entity;
    }

}
