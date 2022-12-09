package site.book.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@ToString(exclude = {"user", "book"})
@Entity(name = "POSTS")
@SequenceGenerator(name = "POSTS_SEQ_GEN", sequenceName = "POSTS_SEQ", initialValue = 1, allocationSize = 1)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POSTS_SEQ_GEN")
    private Integer postId; //PK
    
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    
    @OneToOne
    private Book book;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false, length = 2000)
    private String postContent;
    

    private String postWriter;
    
    @Column(nullable = false)
    private Integer myScore;
   
    public Post update(String title, String postContent) {
        this.title = title;
        this.postContent = postContent;
        
        return this;
    }
}
