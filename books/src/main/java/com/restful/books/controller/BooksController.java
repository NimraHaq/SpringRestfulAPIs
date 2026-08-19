package com.restful.books.controller;

import com.restful.books.entity.Book;
import com.restful.books.exception.BookErrorResponse;
import com.restful.books.exception.BookNotFoundException;
import com.restful.books.request.BookRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Tag(name = "Books Restful API", description = "API for managing books")
@RestController
@RequestMapping("rest")
public class BooksController {
    private List<Book> books;

    public BooksController() {
        initializeBooks();
    }

    private void initializeBooks() {
        books = new ArrayList<>();
        books.addAll(List.of(
                new Book(1, "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 4),
                new Book(2, "To Kill a Mockingbird", "Harper Lee", "Fiction",5),
                new Book(3, "1984", "George Orwell", "Dystopian",4),
                new Book(4, "Pride and Prejudice", "Jane Austen", "Romance",5),
                new Book(5, "The Catcher in the Rye", "J.D. Salinger", "Fiction",3)
        ));
    }

    @Operation(summary = "Get Books", description = "Get all books or filter by category. If no category is provided, all books will be returned.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("books")
    public List<Book> getBooks(@Parameter(description = "Optional category to filter books") @RequestParam(value = "category", required = false) String category) {
        if(books.isEmpty() || Objects.nonNull(books)){
            throw new BookNotFoundException("No books found.");
        }
        if (category == null || category.trim().isEmpty()) {
            return books;
        }
        return books.stream().filter(b -> b.getCategory().equalsIgnoreCase(category.trim())).toList();
    }


    @Operation(summary = "Get Book By Id", description = "Retrieve a specific book from the list by Id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/books/{id}")
    public Book getBooksById(@Parameter(description = "ID of the book to retrieve") @PathVariable("id") @Min(value = 1) long id) {
        return books.stream().filter(b -> b.getId() == id).findFirst().orElseThrow(()-> new BookNotFoundException("Book not found with ID: " + id));
    }

    @Operation(summary = "Add Book", description = "Add a new book to the list")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/addBook")
    public void addBook(@Valid @RequestBody BookRequest newBookRequest){
        long id = books.isEmpty() ? 1 : books.getLast().getId() + 1;
        books.add(mapToBook(id, newBookRequest));
    }

    @Operation(summary = "Update Book", description = "Update an existing book in the list")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/books/{id}")
    public void updateBook(@Parameter(description = "ID of the book to update.") @PathVariable("id") @Min(value = 1) long id, @Valid @RequestBody BookRequest updatedBookRequest){
        int index = books.stream().filter(b -> b.getId() == id).findFirst().map(books::indexOf).orElse(-1);
        if (index != -1) {
            books.set(index, mapToBook(id, updatedBookRequest));
        }
        throw new BookNotFoundException("Book not found with ID: " + id);
    }

    @Operation(summary = "Delete Book", description = "Delete a book from the list")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/books/{id}")
    public void deleteBook(@Parameter(description = "ID of the book to delete") @PathVariable("id") @Min(value = 1) long id){
        Book book =  books.stream().filter(b -> b.getId() == id).findFirst().orElseThrow(()-> new BookNotFoundException("Book not found with ID: " + id));
        books.remove(book);
    }

    private Book mapToBook(long id, BookRequest bookRequest){
        return new Book(id, bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.getCategory(), bookRequest.getRating());
    }

    private BookRequest mapToBookRequest(Book book){
        return new BookRequest(book.getTitle(), book.getAuthor(), book.getCategory(), book.getRating());
    }
}




/*
Spring handles jackson-data-bind in its rest dependencies, we do not need to add it manually.
jackson project handles json to POJO and POJO to json conversion.
we need to have getters and setters in POJO class for jackson to utilize.

REST controllers just return java.
conversion is handled by jackson on backend. we do not need to worry about it.
 */
