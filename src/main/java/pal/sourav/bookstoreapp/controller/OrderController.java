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

import pal.sourav.bookstoreapp.dto.OrderDTO;
import pal.sourav.bookstoreapp.dto.ResponseDTO;
import pal.sourav.bookstoreapp.service.IOrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	IOrderService orderService;
	
	
	
	@PostMapping("/insert")
	public ResponseEntity<ResponseDTO> insertOrder(@RequestBody OrderDTO orderdto) {
		ResponseDTO dto = new ResponseDTO(orderService.insertOrder(orderdto), "Order added successfully !");
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}
	
	
	
	@GetMapping("/getall")
	public ResponseEntity<ResponseDTO> getAllOrders() {
		ResponseDTO dto = new ResponseDTO(orderService.getAllOrders(), "All order records retrieved successfully !");
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	
	@GetMapping("/get-by-id/{orderId}")
	public ResponseEntity<ResponseDTO> getOrderByID(@PathVariable int orderId) {
		ResponseDTO dto = new ResponseDTO(orderService.getOrderByID(orderId), "Order detail retrieved successfully !");
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	
	@PutMapping("/update-by-id/{orderId}")
	public ResponseEntity<ResponseDTO> updateOrder(@PathVariable int orderId, @RequestBody OrderDTO orderdto) {
		ResponseDTO dto = new ResponseDTO(orderService.updateOrder(orderId, orderdto), "Order updated successfully !");
		return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping("/cancel/{orderId}")
	public ResponseEntity<ResponseDTO> cancelOrder(@PathVariable int orderId){
		ResponseDTO dto = new ResponseDTO(orderService.cancelOrder(orderId), "Order Cancelled Successfully");
		return new ResponseEntity<ResponseDTO>(dto, HttpStatus.OK);	
	}
	
	
	@DeleteMapping("/delete-by-id/{orderId}")
	public ResponseEntity<ResponseDTO> deleteOrderById(@PathVariable int orderId) {
		ResponseDTO dto = new ResponseDTO(orderService.deleteOrderById(orderId), "Order deleted successfully !");
		return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
	}
	
	
}
