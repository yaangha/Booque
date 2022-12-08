package site.book.project.dto;

import lombok.Data;
import site.book.project.domain.Post;


@Data
public class PostUpdateDto {

    private Integer postId;
    private String title;
    private String postContent;
    
    public Post toEntity() {
        
        return Post.builder().postId(postId).title(title).postContent(postContent).build();
    }
}
