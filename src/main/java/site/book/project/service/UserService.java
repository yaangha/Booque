package site.book.project.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.User;
import site.book.project.repository.PostRepository;
import site.book.project.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    
    public User read(Integer id) {
        return userRepository.findById(id).get();
    }
    
    
}
