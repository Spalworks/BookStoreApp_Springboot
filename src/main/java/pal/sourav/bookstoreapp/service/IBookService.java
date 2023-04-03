package pal.sourav.bookstoreapp.service;

import java.util.List;

import pal.sourav.bookstoreapp.dto.BookDTO;
import pal.sourav.bookstoreapp.dto.QuantityDTO;
import pal.sourav.bookstoreapp.entity.Book;

public interface IBookService {

	Book insertBook(BookDTO bookDTO);

	List<Book> getAllBooks();

	Book getBookById(int bookId);

	List<Book> getBookByName(String bookName);

	List<Book> sortBooksAsc();

	List<Book> sortBooksDesc();

	Object updateBookById(int bookId, BookDTO bookdto);

	Book updateQuantity(QuantityDTO quantity);

	String deleteBook(int id);

}
