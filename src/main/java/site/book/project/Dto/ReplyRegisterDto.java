package site.book.project.Dto;

import lombok.Data;

@Data
public class ReplyRegisterDto {

    private Integer postId;
    private String replyContent;
    private String replyWriter;
}
