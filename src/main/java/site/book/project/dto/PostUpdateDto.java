package site.book.project.dto;

import lombok.Data;
import site.book.project.domain.Book;
import site.book.project.domain.Post;


@Data
public class PostUpdateDto {

    private Integer postId;
    private Integer bookId;
    private String title;
    private String postContent;
    
    public Post toEntity(Book book) {
        
        return Post.builder().postId(postId).book(book).title(title).postContent(postContent).build();
    }
}
