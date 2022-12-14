package site.book.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Data
@ToString
@NoArgsConstructor
public class OrderFromDetailDto {
    // (하은) 디테일 페이지에서 바로 구매할 때 필요한 정보 - order 테이블에 추가될 것
    
    private Integer userId;
    private Integer id;
    private Integer price;
    private Integer count;

}
