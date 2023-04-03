package pal.sourav.bookstoreapp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import pal.sourav.bookstoreapp.dto.OrderDTO;
import pal.sourav.bookstoreapp.entity.Book;
import pal.sourav.bookstoreapp.entity.OrderModel;
import pal.sourav.bookstoreapp.entity.User;
import pal.sourav.bookstoreapp.exceptions.BookStoreException;
import pal.sourav.bookstoreapp.repository.BookRepository;
import pal.sourav.bookstoreapp.repository.OrderRepository;
import pal.sourav.bookstoreapp.repository.UserRepository;
import pal.sourav.bookstoreapp.util.EmailService;


@Service
@Slf4j
public class OrderService implements IOrderService{
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private BookRepository bookRepo;
	
	@Autowired
	private UserRepository userRepo; 
	
	@Autowired
	private EmailService mailService;
	

	@Override
	public OrderModel insertOrder(OrderDTO orderdto) {
		Optional<Book> book = bookRepo.findById(orderdto.getBookID());
		Optional<User> user = userRepo.findById(orderdto.getUserID());
		
		if (book.isPresent()) {
			int bookPrice = book.get().getPrice();
			if (user.isPresent()) {
				if (orderdto.getQuantity() <= book.get().getQuantity()) {
					book.get().setQuantity(book.get().getQuantity() - orderdto.getQuantity());
					bookRepo.save(book.get());
					log.info("Book stock quantity updated");
					
					OrderModel newOrder = new OrderModel(orderdto, bookPrice);
					orderRepo.save(newOrder);
					log.info("Order inserted Successfully");
					
					mailService.sendEmail(user.get().getEmail(), "Order Placed successfully",
							"Hello " + user.get().getFirstName() + " .\n\n"
							+ "Your order ID is " + newOrder.getOrderID() + "\n"
									+ "Order placed on : " + newOrder.getDate() + "\n"
									+ " The order will be delivered to you shortly."+ "\n\n"
									+ "Thank You for Shopping !!!!");
					log.info("Email sent");
					
					return newOrder;
				} else {
					throw new BookStoreException("Ordered quantity exceeds Stock Limit !!!");
				}
			} else
				throw new BookStoreException("User Not Found !!!");
		} else 
			throw new BookStoreException("Book Not Found !!!");
		
	}

	@Override
	public List<OrderModel> getAllOrders() {
		List<OrderModel> orderList = orderRepo.findAll();
		if (orderList.size() != 0) {
			log.info("ALL order records retrieved successfully");
			return orderList;
		} else
			throw new BookStoreException("No order Found !!!!");	
	}

	
	@Override
	public OrderModel getOrderByID(int orderId) {
		Optional<OrderModel> order = orderRepo.findById(orderId);
		if (order.isEmpty()) {
			throw new BookStoreException("Order Record doesn't exists");
		} else {
			log.info("Order record retrieved successfully for id: " + orderId);
			return order.get();
		}
	}

	@Override
	public OrderModel updateOrder(int orderId, OrderDTO orderdto) {
		Optional<OrderModel> order = orderRepo.findById(orderId);
		Optional<Book> book = bookRepo.findById(orderdto.getBookID());
		Optional<User> user = userRepo.findById(orderdto.getUserID());
		
		if (order.isPresent()){
			if (book.isPresent()) {
				int bookPrice = book.get().getPrice();
				if (user.isPresent()) {
					if (orderdto.getQuantity() <= book.get().getQuantity()) {
						book.get().setQuantity(book.get().getQuantity() - orderdto.getQuantity());
						bookRepo.save(book.get());
						log.info("Book stock quantity updated");
						
						OrderModel newOrder = new OrderModel(orderdto, bookPrice);
						orderRepo.save(newOrder);
						log.info("Updated Order inserted Successfully");
						
						mailService.sendEmail(user.get().getEmail(), "Order Updated successfully",
								"Hello " + user.get().getFirstName() + " .\n\n"
								+ "Your order no. " + newOrder.getOrderID()
										+ "has been updated successfully on : " + newOrder.getDate() + "\n"
										+ " The order will be delivered to you shortly."+ "\n\n"
										+ "Thank You for Shopping !!!!");
						log.info("Email sent");
						
						return newOrder;
					} else
						throw new BookStoreException("Ordered quantity exceeds Stock Limit !!!");
				} else
					throw new BookStoreException("User Not Found !!!");
			} else 
				throw new BookStoreException("Book Not Found !!!");
		} else 
			throw new BookStoreException("Order Not Found !!!");
		
	}
	
	
	@Override
	public OrderModel cancelOrder(int orderId) {
		Optional<OrderModel> order = orderRepo.findById(orderId);
		Optional<Book> book = bookRepo.findById(order.get().getBookID());
		Optional<User> user = userRepo.findById(order.get().getUserID());
		
		if (order.isPresent() && order.get().isCancel() != true) {
			book.get().setQuantity(book.get().getQuantity() + order.get().getQuantity());
			bookRepo.save(book.get());
			log.info("Book Stock quantity is updated");
			
			order.get().setCancel(true);
			orderRepo.save(order.get());
			log.info("Order cancelled successfully !!!");
			
			mailService.sendEmail(user.get().getEmail(), "Order Cancelled",
					"Dear " + user.get().getFirstName() + " ,\n\n"
					+ "Your order no. " + order.get().getOrderID()
							+ "has been cancelled successfully on : " + LocalDate.now() + "\n\n"
							+ "Thank You !!!!");
			log.info("Email sent");
			
			return order.get();
		} else
			throw new BookStoreException("Order Not Found or Already cancelled !!!");
		
	}
	

	@Override
	public Object deleteOrderById(int orderId) {
		Optional<OrderModel> order = orderRepo.findById(orderId);
		Optional<Book> book = bookRepo.findById(order.get().getBookID());
		
		if (order.isPresent()) {
			book.get().setQuantity(book.get().getQuantity() + order.get().getQuantity());
			bookRepo.save(book.get());
			log.info("Book stock quantity updated");
			
			orderRepo.deleteById(orderId);
			return "Order deleted Successfully. Order id : " + orderId;	
		} else
			throw new BookStoreException("Order Record doesn't exists");
	}

	

}
