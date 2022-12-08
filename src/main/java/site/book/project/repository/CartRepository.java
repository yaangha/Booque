package site.book.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import site.book.project.domain.User;

public interface CartRepository extends JpaRepository<User, Integer> {

}
