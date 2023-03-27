package pal.sourav.bookstoreapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pal.sourav.bookstoreapp.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
	
	
}
