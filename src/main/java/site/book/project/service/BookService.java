package site.book.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.book.project.domain.Book;
import site.book.project.repository.BookRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {

	private final BookRepository bookRepository;
	
	public Book read(Integer id) {
		return bookRepository.findById(id).get();
	}
	
}
