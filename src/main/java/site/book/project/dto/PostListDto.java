package site.book.project.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostListDto {

    private Integer postId;
    private String title;
    private String bookImage;
    private LocalDateTime modifiedTime;
    private Integer bookId;
    
}
