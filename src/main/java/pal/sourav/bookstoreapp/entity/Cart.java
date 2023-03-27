package pal.sourav.bookstoreapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pal.sourav.bookstoreapp.dto.CartDTO;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int cartID;
	
	private int userID;
	private int bookID;
	private int quantity;

	
	public Cart(CartDTO cartDTO) {
		this.userID = cartDTO.getUserID();
		this.bookID = cartDTO.getBookID();
		this.quantity = cartDTO.getQuantity();
	}


	public Cart(int cartId2, CartDTO cartdto) {
		this.cartID = cartId2;
		this.userID = cartdto.getUserID();
		this.bookID = cartdto.getBookID();
		this.quantity = cartdto.getQuantity();
	}
}
