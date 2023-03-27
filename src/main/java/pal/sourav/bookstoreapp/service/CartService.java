package pal.sourav.bookstoreapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import pal.sourav.bookstoreapp.dto.CartDTO;
import pal.sourav.bookstoreapp.dto.QuantityDTO;
import pal.sourav.bookstoreapp.entity.Book;
import pal.sourav.bookstoreapp.entity.Cart;
import pal.sourav.bookstoreapp.entity.User;
import pal.sourav.bookstoreapp.exceptions.BookStoreException;
import pal.sourav.bookstoreapp.repository.BookRepository;
import pal.sourav.bookstoreapp.repository.CartRepository;
import pal.sourav.bookstoreapp.repository.UserRepository;

@Slf4j
@Service
public class CartService implements ICartService {

	@Autowired
	CartRepository cartRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	BookRepository bookRepo;

	@Override
	public Object insertIntoCart(CartDTO cartDTO) {
		// checking whether the Book & the cart is present or not
		Optional<Book> book = bookRepo.findById(cartDTO.getBookID());
		Optional<User> user = userRepo.findById(cartDTO.getUserID());

		if (book.isPresent()) {
			log.info("Book Found. Book Name: " + book.get().getBookName());
			if (user.isPresent()) {
				log.info("User Found!!!!");

				if (cartDTO.getQuantity() <= book.get().getQuantity()) {
					Cart newCart = new Cart(cartDTO);
					cartRepo.save(newCart);
					log.info("Cart details inserted successfully!!!!");
					return newCart;
				} else {
					log.info("Quantity exceeds");
					throw new BookStoreException("Requested quantity exceeds the stock quantity");
				}

			} else {
				log.info("User NOT FOUND");
				throw new BookStoreException("User not found");
			}
		} else {
			log.info("Book NOT FOUND");
			throw new BookStoreException("Book Not found !!!");
		}

	}

	@Override
	public Object getAllCartDetails() {
		List<Cart> cartList = cartRepo.findAll();
		if (cartList.size() != 0) {
			log.info("All the cart details data Retrived Successfully");
			return cartList;
		} else {
			log.info("No cart details Found");
			throw new BookStoreException("Empty cart!!!");
		}
	}

	@Override
	public Object getCartDetailByID(int cartId) {
		Optional<Cart> existingCart = cartRepo.findById(cartId);

		if (existingCart.isPresent())
			return existingCart.get();
		else
			throw new BookStoreException("Cart NOT FOUND !!!");
	}

	@Override
	public Object updateCartById(int cartId, CartDTO cartdto) {
		Optional<Cart> existingCart = cartRepo.findById(cartId);  
		
		if (existingCart.isPresent()) {
			Optional<Book> book = bookRepo.findById(cartdto.getBookID());
			Optional<User> user = userRepo.findById(cartdto.getUserID());
		
			if (book.isPresent()) {  log.info("Book Found. Book Name: " + book.get().getBookName());
				if (user.isPresent()) {  log.info("User Found!!!!");
					if (cartdto.getQuantity() <= book.get().getQuantity()) {
						Cart newCart = new Cart(cartId, cartdto);
						cartRepo.save(newCart);
						log.info("Cart details inserted successfully!!!!");
						return newCart;
					} else {
						log.info("Quantity exceeds");
						throw new BookStoreException("Requested quantity exceeds the stock quantity");
					}
				} else {
				log.info("User NOT FOUND");
				throw new BookStoreException("User not found");
				}	
			} else {
				log.info("Book NOT FOUND");
				throw new BookStoreException("Book Not found !!!");
			}
		}else
			throw new BookStoreException("Cart NOT FOUND !!!");

	}

	@Override
	public Object updateQuantity(int cartId, QuantityDTO newQuantity) {
		Optional<Cart> existingCart = cartRepo.findById(cartId);
		Optional<Book> book = bookRepo.findById(newQuantity.getBookID());
		if (existingCart.isPresent()) {
			
			if (book.isPresent()) {
				if (newQuantity.getNewQuantity() <= book.get().getQuantity()) {
					existingCart.get().setQuantity(newQuantity.getNewQuantity());
					cartRepo.save(existingCart.get());
					log.info("Quantity in cart updated successfully");
					bookRepo.save(book.get());
					return existingCart.get();
				} else
					throw new BookStoreException("Requested quantity exceeds the stock quantity");
			}else 
				throw new BookStoreException("Book NOT FOUND in cart !!!");

		} else
			throw new BookStoreException("Cart NOT FOUND !!!");
	}

	@Override
	public String deleteCartById(int cartId) {
		Optional<Cart> existingCart = cartRepo.findById(cartId);

		if (existingCart.isPresent()) {
			cartRepo.deleteById(cartId);
			return "Cart data deleted successfully!  Cart id : " + cartId;
		} else
			throw new BookStoreException("Cart NOT FOUND !!!");
	}

}
