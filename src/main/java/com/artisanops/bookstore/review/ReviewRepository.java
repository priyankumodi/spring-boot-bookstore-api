package com.artisanops.bookstore.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBookId(Long book_id);
    Optional<Review> findByIdAndBookId(Long id, Long bookId);
}
