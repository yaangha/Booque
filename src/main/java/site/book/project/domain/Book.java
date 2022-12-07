package site.book.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

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
@Entity(name = "BOOKS")
@SequenceGenerator(name = "BOOKS_SEQ_GEN", sequenceName = "BOOKS_SEQ", initialValue = 1, allocationSize = 1)
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOKS_SEQ_GEN")
    private Integer bookId; // PK
    
    @Column(nullable = false)
    private String bookName;
    
    @Column(nullable = false)
    private String publisher;
    
    @Column(nullable = false)
    private String publishedDate;
    
   
    private String bookImage;
    
    @Column(nullable = false)
    private String author;
    
    @Column(nullable = false)
    private String pages;
    
    @Column(nullable = false)
    private int prices;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false)
    private String bookIntroImage;
}
