package site.book.project.dto;

import lombok.Data;

// 책 상세 페이지 밑에 있는 한줄평

@Data
public class BookCommentRegisterDto {

    private Integer bookId;
    private String commentText;
    private String commentWriter;
    
    // 유저 번호 필요함
    private Integer userId;
    
}
