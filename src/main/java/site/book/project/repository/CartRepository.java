package site.book.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import site.book.project.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{

    List<Cart> findByUserId(Integer userId);
    
    
    
}
