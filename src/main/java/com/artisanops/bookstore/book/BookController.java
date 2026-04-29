package com.artisanops.bookstore.book;

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
 * Book API Endpoints:
 * Get All Books:    GET     /books
 * Get Book by ID:   GET     /books/{id}
 * Create a Book:    POST    /books
 * Update a Book:    PUT     /books/{id}
 * Delete a Book:    DELETE  /books/{id}
 */

@RestController
@RequestMapping("/books")
// The Tag annotation groups all these endpoints under "Book Module" in the UI
@Tag(name = "Book Module", description = "Endpoints for managing the bookstore inventory")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(
            summary = "Get all books",
            description = "Retrieves a full list of all books available in the bookstore database."
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of books")    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @Operation(
            summary = "Get a book by ID",
            description = "Fetches a single book's details using its unique database ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book details"),
            @ApiResponse(responseCode = "404", description = "Book not found - ID does not exist")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO bookDTO = bookService.getBookById(id);
        if (bookDTO != null) {
            return new ResponseEntity<>(bookDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Create a new book",
            description = "Adds a new book record to the system. Requires ISBN, Title, and Description."
    )
    @ApiResponse(responseCode = "201", description = "Book successfully created")
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        BookDTO bookDTOCreated = bookService.createBook(bookDTO);
        return new ResponseEntity<>(bookDTOCreated, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update an existing book",
            description = "Updates the information for an existing book. Returns 404 if the ID is not found."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "404", description = "Update failed - Book ID not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id,@Valid @RequestBody BookDTO bookDTO) {
        BookDTO bookDTOUpdated = bookService.updateBook(id, bookDTO);
        return bookDTOUpdated != null ? new ResponseEntity<>(bookDTOUpdated, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Delete a book",
            description = "Removes a book from the bookstore inventory permanently."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Delete failed - Book ID not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Boolean status = bookService.deleteBook(id);
        return status ? new ResponseEntity<>(HttpStatus.NO_CONTENT): new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
