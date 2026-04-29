package com.artisanops.bookstore.review;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Review API Endpoints:
 * Get All Reviews for a Book:  GET     /books/{bookId}/reviews
 * Get a Specific Review:       GET     /books/{bookId}/reviews/{reviewId}
 * Create a Review:             POST    /books/{bookId}/reviews
 * Update a Review:             PUT     /books/{bookId}/reviews/{reviewId}
 * Delete a Review:             DELETE  /books/{bookId}/reviews/{reviewId}
 */

@RestController
@RequestMapping("/books/{bookId}/reviews")
@Tag(name = "Review Module", description = "Endpoints for managing reviews linked to specific books")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(
            summary = "Get all reviews for a book",
            description = "Retrieves all reviews associated with a specific book ID."
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved reviews")
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews(@PathVariable Long bookId) {
        List<ReviewDTO> reviewsDTO = reviewService.getAllReviews(bookId);
        return new ResponseEntity<>(reviewsDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Get a specific review",
            description = "Fetches a specific review by its ID, ensuring it belongs to the specified book."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review found"),
            @ApiResponse(responseCode = "404", description = "Review not found for this book")
    })
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long bookId,
                                                    @PathVariable Long reviewId) {
        ReviewDTO reviewDTO = reviewService.getReviewById(bookId, reviewId);
        return (reviewDTO == null) ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Create a review for a book",
            description = "Submits a new review. The review will be automatically linked to the book specified in the URL."
    )
    @ApiResponse(responseCode = "201", description = "Review created successfully")
    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@PathVariable Long bookId,
                                                  @Valid @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO reviewDTOCreated = reviewService.createReview(bookId, reviewDTO);
        return new ResponseEntity<>(reviewDTOCreated, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update a review",
            description = "Updates an existing review. Note: The book association cannot be changed via this endpoint."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review updated successfully"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long bookId,
                                                  @PathVariable Long reviewId,
                                                  @Valid @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO reviewDTOUpdated = reviewService.updateReview(bookId, reviewId, reviewDTO);
        return (reviewDTOUpdated == null) ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(reviewDTOUpdated, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a review",
            description = "Permanently removes a review from a book."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Review deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> deleteReview(@PathVariable Long bookId,
                                                  @PathVariable Long reviewId) {
        Boolean isDeleted = reviewService.deleteReview(bookId, reviewId);
        return (isDeleted) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
