package site.book.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import site.book.project.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
