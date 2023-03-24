package pal.sourav.bookstoreapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantityDTO {
	
	private int bookID;
	private int newQuantity;
}
