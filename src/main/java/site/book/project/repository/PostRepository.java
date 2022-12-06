package site.book.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import site.book.project.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
