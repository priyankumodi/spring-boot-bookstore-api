package com.artisanops.bookstore.book;

import java.util.List;

public interface BookService {
    List<BookDTO> getAllBooks();
    BookDTO getBookById(Long id);
    BookDTO createBook(BookDTO dto);
    BookDTO updateBook(Long id, BookDTO dto);
    Boolean deleteBook(Long id);
}
