package pal.sourav.bookstoreapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pal.sourav.bookstoreapp.entity.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	@Query(value = "select * from book WHERE book_name LIKE :bookName%", nativeQuery = true)
	public List<Book> findByBookName(String bookName);

	@Query(value = "select * from book ORDER BY price", nativeQuery = true)
	public List<Book> sortBooksAsc();

	@Query(value = "select * from book ORDER BY price DESC", nativeQuery = true)
	public List<Book> sortBooksDesc();

	
}