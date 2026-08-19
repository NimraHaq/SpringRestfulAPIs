package com.restful.books.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BookRequest {

    //annotations do not work till @Valid annotation is added with the parameter in endpoint argument
    @Size(min=1, max = 30, message = "Title must be between 1 and 30 characters")
    @NotBlank(message = "Title is required")
    private String title;

    @Size(min=1, max = 40, message = "Author name must be between 1 and 40 characters")
    @NotBlank(message = "Author name is required")
    private String author;

    @Size(min=1, max = 30, message = "Category must be between 1 and 30 characters")
    @NotBlank(message = "Category is required")
    private String category;

    @Min(value = 1, message = "Rating cannot be less than 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    private int rating;

    // Constructors
    public BookRequest() {}

    public BookRequest(String title, String author, String category, int rating) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
