package com.artisanops.bookstore.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

public class ReviewDTO {
    private Long id;

    @NotBlank(message = "Review title is required")
    private String title;

    @NotBlank(message = "Review content cannot be empty")
    private String content;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot be more than 5")
    private double rating;

    @NotBlank(message = "Reviewer name is required")
    private String reviewerName;

    @PastOrPresent(message = "Review date cannot be in the future")
    private LocalDateTime postedAt;

    public ReviewDTO() {
    }

    public ReviewDTO(Long id, String title, String content, double rating, String reviewerName, LocalDateTime postedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.reviewerName = reviewerName;
        this.postedAt = postedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDateTime postedAt) {
        this.postedAt = postedAt;
    }
}
