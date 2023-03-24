package pal.sourav.bookstoreapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pal.sourav.bookstoreapp.dto.BookDTO;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int bookId;
	
	private String bookName;
	private String authorName;
	private String bookDescription;
	private String bookImg;  
	private int price;
	private int quantity;
	
	
	
	public Book(BookDTO bookDTO) {
		
		this.bookName = bookDTO.getBookName();
		this.authorName = bookDTO.getAuthorName();
		this.bookDescription = bookDTO.getBookDescription();
		this.bookImg = bookDTO.getBookImg();  
		this.price = bookDTO.getPrice();
		this.quantity = bookDTO.getQuantity();
	}



	public Book(int bookId2, BookDTO bookDTO) {
		this.bookId = bookId2;
		
		this.bookName = bookDTO.getBookName();
		this.authorName = bookDTO.getAuthorName();
		this.bookDescription = bookDTO.getBookDescription();
		this.bookImg = bookDTO.getBookImg();  
		this.price = bookDTO.getPrice();
		this.quantity = bookDTO.getQuantity();
	}
	
	
} 
