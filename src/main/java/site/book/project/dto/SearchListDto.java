package site.book.project.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchListDto {

    private Integer BookId;
    private Integer reviewCount;
    
}
