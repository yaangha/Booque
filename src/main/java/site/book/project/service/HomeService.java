package site.book.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.repository.BookRepository;
import site.book.project.repository.CategoryRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeService {
    
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    
    // 전체 별점 Top 4
    @Transactional(readOnly = true)
    public List<Book> readAllRankingOrderByBookScore() {
        List<Book> list = new ArrayList<>();
        list = bookRepository.findTop4ByOrderByBookScoreDesc();
        
        return list;
    }

    // 전체 리뷰 Top 4
    @Transactional(readOnly = true)
    public List<Book> readAllRankingOrderByPostReview() {
        List<Book> list = new ArrayList<>();
        list = bookRepository.findTop4ByOrderByPostCountDesc();
//        
//        List<SearchListDto> reviewCount = new ArrayList<>();
//        for (Post p : list) {
//            
//            Integer count = postService.countPostByBookId(p.getBook().getBookId());
//            
//            SearchListDto reviewElement = SearchListDto.builder().BookId(p.getBook().getBookId()).reviewCount(count).build();
//            reviewCount.add(reviewElement);
//        }
//        
//        List<SearchReadDto> reviewList = new ArrayList<>();
//        for (Post p : list) {
//            for (SearchListDto s : reviewCount) {
//                if (p.getBook().getBookId().equals(s.getBookId())) {
//                    SearchReadDto dto = SearchReadDto.builder().bookId(p.getBook().getBookId()).bookName(p.getBook().getBookName())
//                            .bookImage(p.getBook().getBookImage()).author(p.getBook().getAuthor()).publisher(p.getBook().getPublisher())
//                            .publishedDate(p.getBook().getPublishedDate()).reviewCount(s.getReviewCount()).build();
//                    reviewList.add(dto);                    
//                }
//            }
//        }
//        
//        // 리뷰순으로 오름차순 정렬
//        reviewList.sort(new Comparator<SearchReadDto>() {
//            @Override
//            public int compare(SearchReadDto arg0, SearchReadDto arg1) {
//                int reviewCount0 = arg0.getReviewCount();
//                int reviewCount1 = arg1.getReviewCount();
//                
//                if(reviewCount0 == reviewCount1) return 0;
//                else if(reviewCount0 > reviewCount1) return -1;
//                else return 1;
//            }
//        });
        
        return list;
    }

    // 카테고리별 별점 Top 4
    @Transactional(readOnly = true)
    public List<Book> readAllRankingCategoryOrderByBookScore(String category) {
        List<Book> list = new ArrayList<>();
        list = categoryRepository.findTop4ByCategoryOrderByBookScoreDesc(category);
        
        return list;
    }
    
    // 카테고리별 리뷰순 Top 4
    @Transactional(readOnly = true)
    public List<Book> readAllRankingCategoryOrderByBookReview(String category) {
        List<Book> list = new ArrayList<>();
        list = categoryRepository.findTop4ByCategoryOrderByPostCountDesc(category);
        
        return list;
    }

}
