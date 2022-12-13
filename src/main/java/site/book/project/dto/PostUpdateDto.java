package site.book.project.dto;

import lombok.Data;
import site.book.project.domain.Post;


@Data
public class PostUpdateDto {

    private Integer postId;
    private Integer bookId;
    private String title;
    private String postContent;
    
    public Post toEntity() {
        
        return Post.builder().postId(postId).bookId(bookId).title(title).postContent(postContent).build();
    }
}
