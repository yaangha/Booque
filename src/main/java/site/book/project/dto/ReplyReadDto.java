package site.book.project.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import site.book.project.domain.PostReply;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class ReplyReadDto {

    private Integer replyId;
    private Integer postId;
    private String replyContent;
    private String replyWriter;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    
    public static ReplyReadDto fromEntity(PostReply entity) {
        return ReplyReadDto.builder().replyId(entity.getReplyId())
                .postId(entity.getPost().getPostId())
               .replyWriter(entity.getReplyWriter())
                .replyContent(entity.getReplyContent())
                .createdTime(entity.getCreatedTime())
                .modifiedTime(entity.getModifiedTime())
                .build();
    }
}
