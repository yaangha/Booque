package site.book.project.dto;

import lombok.Data;

@Data
public class ReplyRegisterDto {

    private Integer postId;
    private String replyContent;
    private String replyWriter;
}
