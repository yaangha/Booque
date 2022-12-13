package site.book.project.dto;

import lombok.Data;

@Data
public class SearchReadDto {
    
    private Integer boookId;
    private String bookImage;
    private String bookName;
    private String author;
    private String publisher;
    private String publishedDate;
    private int prices;
    private Integer myScore;
    private int reviewCount;
}
