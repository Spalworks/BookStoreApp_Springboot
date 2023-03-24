package pal.sourav.bookstoreapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import pal.sourav.bookstoreapp.dto.BookDTO;
import pal.sourav.bookstoreapp.dto.QuantityDTO;
import pal.sourav.bookstoreapp.entity.Book;
import pal.sourav.bookstoreapp.exceptions.BookStoreException;
import pal.sourav.bookstoreapp.repository.BookRepository;

@Service
@Slf4j
public class BookService implements IBookService {
	
	@Autowired
	private BookRepository bookRepo;

	
//storing new Book with Details in db 
	@Override
	public Book insertBook(BookDTO bookDTO) {
		Book book = new Book(bookDTO);
		bookRepo.save(book);
		log.info("Book is stored");
		return book;
	}

	
	
	
//getting the list of all the books present in the db
	@Override
	public List<Book> getAllBooks() {
		List<Book> bookList = bookRepo.findAll();
		if (bookList.size() != 0)
			return bookList;
		
		throw new BookStoreException("No Book found!!!");
	}

//getting book details from BookId
	@Override
	public Book getBookById(int bookId) {
		Optional<Book> response = bookRepo.findById(bookId);
		return response.orElseThrow(() -> new BookStoreException("Book not found with this Id : " + bookId));
	}
	
	
//getting book details from BookId
	@Override
	public Book getBookByName(String bookName) {
		Optional<Book> response = bookRepo.findByBookName(bookName);
		return response.orElseThrow(() -> new BookStoreException("Book not found with this name : " + bookName));
	}

//sorting all the books in ascending order according to the price
	@Override
	public List<Book> sortBooksAsc() {
		List<Book> bookList = bookRepo.sortBooksAsc();
		if (bookList.size() != 0) {
			log.info("Book sorted in Ascending order");
			return bookList;
		}
		throw new BookStoreException("No Book found!!!");
	}

	
//sorting all the books in descending order according to the price
	@Override
	public List<Book> sortBooksDesc() {
		List<Book> bookList = bookRepo.sortBooksDesc();
		if (bookList.size() != 0) {
			log.info("Book sorted in Descending order");
			return bookList;
		}
		throw new BookStoreException("No Book found!!!");
	}

	
	
	
//getting existing book using bookId  & update book data using bookDto	
	@Override
	public Object updateBookById(int bookId, BookDTO bookdto) {
		Optional<Book> book = bookRepo.findById(bookId);
		if (book.isEmpty()) {
			throw new BookStoreException("No book found with id : " + bookId);
		} else {
			Book newBook = new Book(bookId, bookdto);
			bookRepo.save(newBook);
			log.info("Book data updated successfully for id " + bookId);
			return newBook;
		}
	}

	
//updating the quantity of a book
	@Override
	public Book updateQuantity(QuantityDTO quantity) {
		Optional<Book> book = bookRepo.findById(quantity.getBookID());
		if (book.isEmpty()) {
			throw new BookStoreException("No book found with id : " + quantity.getBookID());
		} else {
			book.get().setQuantity(quantity.getNewQuantity());
			bookRepo.save(book.get());
			log.info("Quantity of book is updated successfully. Bookid: %d , Quantity: %d " , quantity.getBookID(), quantity.getNewQuantity());
			return book.get();
		}
	}

	
	
	
	
//deleting a book from db using bookId	
	@Override
	public String deleteBook(int bookId) {
		Optional<Book> book = bookRepo.findById(bookId);
		if (book.isEmpty()) {
			throw new BookStoreException("No book found with id : " + bookId);
		} else {
			bookRepo.deleteById(bookId);
			log.info("Book with id : " + bookId + " deleted successfully !!!");
			return "Deleted book id : " + bookId;
		}
	}

	
	
}
