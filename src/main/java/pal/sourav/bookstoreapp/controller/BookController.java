package pal.sourav.bookstoreapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pal.sourav.bookstoreapp.dto.BookDTO;
import pal.sourav.bookstoreapp.dto.QuantityDTO;
import pal.sourav.bookstoreapp.dto.ResponseDTO;
import pal.sourav.bookstoreapp.entity.Book;
import pal.sourav.bookstoreapp.service.IBookService;

@RestController
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	IBookService bookService;

	@PostMapping(value = "/insert")
	public ResponseEntity<ResponseDTO> insertBook(@Valid @RequestBody BookDTO bookDTO){
		Book result = bookService.insertBook(bookDTO);
		ResponseDTO responseDTO = new ResponseDTO(result, "Book inserted successfully.");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.CREATED);
	}
	
	@GetMapping("/get-all-books")
	public ResponseEntity<ResponseDTO> getAllBooks() {
		List<Book> bookList = bookService.getAllBooks();
		ResponseDTO responseDTO = new ResponseDTO(bookList, "All records retrieved successfully !");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.FOUND);
	}
	
	@GetMapping("/get-book-by-id/{bookId}")
	public ResponseEntity<ResponseDTO> getBookById(@PathVariable int bookId) {
		Book result = bookService.getBookById(bookId);
		ResponseDTO responseDTO = new ResponseDTO(result, "Record retrieved successfully !");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.FOUND);
	}
	
	@GetMapping("/get-book-by-name/{bookName}")
	public ResponseEntity<ResponseDTO> getBookByName(@PathVariable String bookName) {
		Book result = bookService.getBookByName(bookName);
		ResponseDTO responseDTO = new ResponseDTO(result, "Record for particular book retrieved successfully !");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.FOUND);
	}
	
	@GetMapping("/sort-asc")
	public ResponseEntity<ResponseDTO> sortBooksAsc() {
		List<Book> bookList = bookService.sortBooksAsc();
		ResponseDTO responseDTO = new ResponseDTO(bookList, "Book are sorted in ascending order successfully !!!");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	
	@GetMapping("/sort-desc")
	public ResponseEntity<ResponseDTO> sortBooksDesc() {
		List<Book> bookList = bookService.sortBooksDesc();
		ResponseDTO responseDTO = new ResponseDTO(bookList, "Book are sorted in descending order successfully !!!");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	
	
	@PutMapping("/update-book-by-id/{bookId}")
	public ResponseEntity<ResponseDTO> updateBookById(@PathVariable int bookId, @Valid @RequestBody BookDTO bookdto) {
		ResponseDTO dto = new ResponseDTO(bookService.updateBookById(bookId, bookdto), "Record updated successfully !");
		return new ResponseEntity<ResponseDTO>(dto, HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("/update-book-quantity")
	public ResponseEntity<ResponseDTO> updateQuantity(@Valid @RequestBody QuantityDTO quantity) {
		Book newBook = bookService.updateQuantity(quantity);
		ResponseDTO responseDTO = new ResponseDTO(newBook, "Quantity for book record updated successfully !");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteBook/{id}")
	public ResponseEntity<ResponseDTO> deleteBook(@PathVariable int id) {
		String result = bookService.deleteBook(id);
		ResponseDTO dto = new ResponseDTO(result, "Record deleted successfully !");
		return new ResponseEntity<ResponseDTO>(dto, HttpStatus.ACCEPTED);
	}
	
	
}
