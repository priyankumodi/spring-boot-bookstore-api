package com.artisanops.bookstore.review.impl;

import com.artisanops.bookstore.book.BookRepository;
import com.artisanops.bookstore.review.Review;
import com.artisanops.bookstore.review.ReviewDTO;
import com.artisanops.bookstore.review.ReviewRepository;
import com.artisanops.bookstore.review.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<ReviewDTO> getAllReviews(Long bookId) {
        return reviewRepository
                .findByBookId(bookId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public ReviewDTO getReviewById(Long bookId, Long reviewId) {
        Optional<Review> reviewOptional = reviewRepository.findByIdAndBookId(reviewId, bookId);
        return reviewOptional
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public ReviewDTO createReview(Long bookId, ReviewDTO reviewDTO) {
        return bookRepository
                .findById(bookId)
                .map(book -> {
                    Review review = new Review();
                    review.setTitle(reviewDTO.getTitle());
                    review.setContent(reviewDTO.getContent());
                    review.setRating(reviewDTO.getRating());
                    review.setReviewerName(reviewDTO.getReviewerName());

                    review.setPostedAt(LocalDateTime.now());
                    review.setBook(book);

                    return convertToDTO(reviewRepository.save(review));
                }).orElse(null);
    }

    @Override
    public ReviewDTO updateReview(Long bookId, Long reviewId, ReviewDTO reviewDTO) {
        Optional<Review> reviewOptional = reviewRepository.findByIdAndBookId(reviewId, bookId);

        if (reviewOptional.isEmpty()) {
            return null;
        }

        Review review = reviewOptional.get();
        review.setTitle(reviewDTO.getTitle());
        review.setContent(reviewDTO.getContent());
        review.setRating(reviewDTO.getRating());
        review.setReviewerName(reviewDTO.getReviewerName());
        //review.setPostedAt(reviewDTO.getPostedAt());
        return convertToDTO(reviewRepository.save(review));
    }

    @Override
    public Boolean deleteReview(Long bookId, Long reviewId) {
        Optional<Review> reviewOptional = reviewRepository.findByIdAndBookId(reviewId, bookId);

        if (reviewOptional.isEmpty()) {
            return false;
        }

        reviewRepository.deleteById(reviewId);
        return true;
    }

    private ReviewDTO convertToDTO(Review review) {
        if (review == null) {
            return null;
        }

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setTitle(review.getTitle());
        reviewDTO.setContent(review.getContent());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setReviewerName(review.getReviewerName());
        reviewDTO.setPostedAt(review.getPostedAt());
        return reviewDTO;
    }
}
