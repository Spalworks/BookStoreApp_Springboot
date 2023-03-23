package pal.sourav.bookstoreapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pal.sourav.bookstoreapp.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String emailId);

}
