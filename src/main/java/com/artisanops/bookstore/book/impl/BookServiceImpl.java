package com.artisanops.bookstore.book.impl;

import com.artisanops.bookstore.book.Book;
import com.artisanops.bookstore.book.BookDTO;
import com.artisanops.bookstore.book.BookRepository;
import com.artisanops.bookstore.book.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public BookDTO getBookById(Long id) {
        return bookRepository
                .findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public BookDTO createBook(BookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setIsbn(dto.getIsbn());
        return convertToDTO(bookRepository.save(book));
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO dto) {
        return bookRepository
                .findById(id)
                .map(existingBook -> {
                    existingBook.setTitle(dto.getTitle());
                    existingBook.setDescription(dto.getDescription());
                    existingBook.setIsbn(dto.getIsbn());
                    return convertToDTO(bookRepository.save(existingBook));
                })
                .orElse(null);
    }

    @Override
    public Boolean deleteBook(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if(bookOptional.isEmpty()) {
            return false;
        }
        bookRepository.deleteById(id);
        return true;
    }

    private BookDTO convertToDTO(Book book) {
        if (book == null)
            return null;

        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setIsbn(book.getIsbn());
        return dto;
    }
}
