package site.book.project.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity(name= "ORDERS")
@SequenceGenerator(name = "ORDERS_SEQ_GEN", sequenceName = "ORDERS_SEQ", initialValue = 1, allocationSize = 1)
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERS_SEQ_GEN")
    private Integer orderId; // 안써도 되지만 PK는 필요해서 넣은 것
    
    @Column(nullable = false) 
    private Integer orderNo; // 주문번호, PK아님
    
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; 
        
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;
    
    @CreatedDate // 주문하는 순간의 시간
    private LocalDateTime orderDate;
    
    @Column(nullable = false)
    private int orderBookCount; 
    
    @Column(nullable = false)
    private int total; 
    
    
}
