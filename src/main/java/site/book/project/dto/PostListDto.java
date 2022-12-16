package site.book.project.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import site.book.project.domain.Post;

@Builder
@Data
public class PostListDto {

    private Integer postId; 
    private Integer bookId;
    private Integer userId;
    
    private String title;
    private String postContent;
    private String bookImage;
    private String postWriter;
    private LocalDateTime modifiedTime;
   
    public PostListDto fromEntity(Post p) {
        
        return PostListDto.builder()
                .userId(p.getUser().getId())
                .postId(p.getPostId())
                .title(p.getTitle())
                .postContent(p.getPostContent())
                .postWriter(p.getPostWriter())
                .bookId(p.getBook().getBookId())
                .bookImage(p.getBook().getBookImage()).modifiedTime(p.getModifiedTime()).build();
    }
    
}
