package pal.sourav.bookstoreapp.controller;

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

import pal.sourav.bookstoreapp.dto.CartDTO;
import pal.sourav.bookstoreapp.dto.QuantityDTO;
import pal.sourav.bookstoreapp.dto.ResponseDTO;
import pal.sourav.bookstoreapp.service.ICartService;


@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	ICartService cartService;
	
	
	@PostMapping("/insert")
	public ResponseEntity<ResponseDTO> insertIntoCart(@RequestBody CartDTO cartDTO){
		ResponseDTO responseDTO = new ResponseDTO(cartService.insertIntoCart(cartDTO), "Book added to cart successfully !!!");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/get-all-details")
	public ResponseEntity<ResponseDTO> getAllCartDetails(){
		ResponseDTO responseDTO = new ResponseDTO(cartService.getAllCartDetails(), "All the records of Cart retrieved successfully !!!");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.FOUND);
	}
	
	
	@GetMapping("/get-details-by-id/{cartId}")
	public ResponseEntity<ResponseDTO> getCartDetailByID(@PathVariable int cartId){
		ResponseDTO responseDTO = new ResponseDTO(cartService.getCartDetailByID(cartId), "Cart record retrieved successfully with id : " + cartId +" !!!");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.FOUND);
	}
	
	
	
	@PutMapping("/update-by-id/{cartId}")
	public ResponseEntity<ResponseDTO> updateCartById(@PathVariable int cartId, @RequestBody CartDTO cartdto) {
		ResponseDTO responseDTO = new ResponseDTO(cartService.updateCartById(cartId, cartdto), "Cart details updated successfully !!!");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.ACCEPTED);
	}
	
	
	
	@PutMapping("/update-Quantity/{cartId}")
	public ResponseEntity<ResponseDTO> updateQuantity(@PathVariable int cartId, @RequestBody QuantityDTO newQuantity) {
		ResponseDTO responseDTO = new ResponseDTO(cartService.updateQuantity(cartId, newQuantity), "Quantity of book in cart updated successfully !!!");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.ACCEPTED);
	}
	
	
	@DeleteMapping("/delete-cart-by-id/{cartId}")
	public ResponseEntity<ResponseDTO> deleteCartById(@PathVariable int cartId) {
		ResponseDTO dto = new ResponseDTO(cartService.deleteCartById(cartId), "Cart data deleted successfully !");
		return new ResponseEntity<ResponseDTO>(dto, HttpStatus.OK);
	}
	
	
	
}
