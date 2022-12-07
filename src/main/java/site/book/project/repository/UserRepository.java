package site.book.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import site.book.project.domain.User;


public interface UserRepository extends JpaRepository<User, Integer> {


    // 아이디로 유저 정보 찾기
    // @Query("select u from USERS u where u.username = :username")
    // User findByName(@Param(value="username") String username);
}
