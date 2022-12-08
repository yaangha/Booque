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
        
        Optional<User> result = userRepository.findByUsername(username);
        if (result.isPresent()) {
                return "nok";
            } else {
                return "ok"; 
            }
    }

    public User registerUser(UserRegisterDto dto) {
        log.info("registerMember(dto = {})", dto);
        
        // 로그인 비밀번호를 암호화한 후 DB에 insert
        User entity = userRepository.save(dto.toEntity());
        log.info("entity = {}", entity);
        
        return entity;
    }
    
    // TODO: 은정 코드로 수정 - (하은)
    public User read(Integer id) {
        
        User user = userRepository.findById(id).get();
        
        return user;
    }

}