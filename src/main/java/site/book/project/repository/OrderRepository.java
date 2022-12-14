package site.book.project.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import site.book.project.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByOrderByOrderIdDesc();
    
    // (하은) orderNo가 동일한 데이터 불러오기
    List<Order> findByOrderNo(Integer orderNo);

}
