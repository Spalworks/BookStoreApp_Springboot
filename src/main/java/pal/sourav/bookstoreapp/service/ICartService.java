package pal.sourav.bookstoreapp.service;

import pal.sourav.bookstoreapp.dto.CartDTO;
import pal.sourav.bookstoreapp.dto.QuantityDTO;

public interface ICartService {

	Object insertIntoCart(CartDTO cartDTO);

	Object getAllCartDetails();

	Object getCartDetailByID(int cartId);

	Object updateCartById(int cartId, CartDTO cartdto);

	Object updateQuantity(int cartId, QuantityDTO newQuantity);

	String deleteCartById(int cartId);



}
