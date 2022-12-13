package site.book.project.service;


import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.User;
import site.book.project.dto.UserRegisterDto;
import site.book.project.dto.UserSigninDto;
import site.book.project.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    
    public String checkUsername(String username) {
        log.info("checkUsername(username = {})", username);
        
        Optional<User> result = userRepository.findByUsername(username);
        if (result.isPresent()) {
                return "namenok";
            } else {
                return "nameok"; 
            }
    }
    
    public String checkNickname(String nickname) {
        log.info("checkNickname(nickname = {})", nickname);
        
        Optional<User> result = userRepository.findByNickName(nickname);
        if (result.isPresent()) {
            return "nicknok";
        } else {
            return "nickok";
        }
    }

    public User registerUser(UserRegisterDto dto) {

        log.info("registerMember(dto = {})", dto);
        
        // 로그인 비밀번호를 암호화한 후 DB에 insert
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User entity = userRepository.save(dto.toEntity());
        log.info("entity = {}", entity);
        
        return entity;
    }
    
    public User read(Integer userId) {
        return userRepository.findById(userId).get();
    }

    public String checkEmail(String email) {

        Optional<User> result = userRepository.findByEmail(email);
        if (result.isPresent() ) {
            return "emailnok";
        } else {
            return "emailok";
        }
    }

    public String checkPw(String username, String password) {
        log.info("checkPw userid = {} password = {}", username, password);
        User user = userRepository.findByUsername(username).get();
        log.info("checkPassword user = {}", user);
        String encodingPw = user.getPassword();
        log.info(encodingPw);
        Boolean confirm = confirm(password, encodingPw);
        log.info("confirm = {}", confirm);
        
        if (confirm == true) {
            return "ok";
        } else {
            return "nok";
        }
    }

    private Boolean confirm(String password, String password2) {
        return passwordEncoder.matches(password, password2);
    }

    public Optional<User> getUserBySigninId(String username) {
        return userRepository.findByUsername(username);
    }

    


}

