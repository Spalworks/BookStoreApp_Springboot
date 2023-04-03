package pal.sourav.bookstoreapp.service;

import java.util.List;

import pal.sourav.bookstoreapp.dto.OrderDTO;
import pal.sourav.bookstoreapp.entity.OrderModel;


public interface IOrderService {

	OrderModel insertOrder(OrderDTO orderdto);

	List<OrderModel> getAllOrders();

	OrderModel getOrderByID(int orderId);

	OrderModel updateOrder(int orderId, OrderDTO orderdto);

	Object deleteOrderById(int orderId);

	OrderModel cancelOrder(int orderId);
	
}
