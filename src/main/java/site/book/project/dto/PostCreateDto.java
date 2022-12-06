package site.book.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import site.book.project.domain.Post;
import site.book.project.domain.User;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class PostCreateDto {

    private String title;
    private String postContent;
    private String postWriter; 
    private Integer myScore;
    
    public Post toEntity() {
        
        return Post.builder().title(title).postContent(postContent).postWriter(postWriter).myScore(myScore).build();
    }
}
