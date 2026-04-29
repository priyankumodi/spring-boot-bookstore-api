package com.artisanops.bookstore.review;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getAllReviews(Long bookId);
    ReviewDTO getReviewById(Long bookId, Long reviewId);
    ReviewDTO createReview(Long bookId, ReviewDTO reviewDTO);
    ReviewDTO updateReview(Long bookId, Long reviewId, ReviewDTO reviewDTO);
    Boolean deleteReview(Long bookId, Long reviewId);
}
