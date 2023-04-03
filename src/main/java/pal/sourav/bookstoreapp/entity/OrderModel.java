package pal.sourav.bookstoreapp.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pal.sourav.bookstoreapp.dto.OrderDTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel {
	
	@Id
	@GeneratedValue
	private int orderID;
	
	private LocalDate date;
	private int totalPrice;
	private int quantity;
	private String address;
	private int userID;
	private int bookID;
	private boolean cancel;
	
	
	public OrderModel(OrderDTO orderdto, int bookPrice) {
		this.date = LocalDate.now();
		this.totalPrice = orderdto.getQuantity() * bookPrice;
		this.quantity = orderdto.getQuantity();
		this.address = orderdto.getAddress();
		this.userID = orderdto.getUserID();
		this.bookID = orderdto.getBookID();
		this.cancel = false;
	}
	
	
	
	
	
	
}
