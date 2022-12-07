package site.book.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import site.book.project.domain.User;


public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findByUsername(String username);
    
}