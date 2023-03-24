package pal.sourav.bookstoreapp.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pal.sourav.bookstoreapp.dto.UserDTO;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int userID;
	
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String phoneNumber;
	private Date dob;
	private String password;
	private boolean tokenStatus;
	
	


	public User(UserDTO userData) {
		this.firstName = userData.getFirstName();
		this.lastName = userData.getLastName();
		this.email = userData.getEmail();
		this.address = userData.getAddress();
		this.phoneNumber = userData.getPhoneNumber();
		this.dob = userData.getDob();
		this.password = userData.getPassword();
	}




	public User(int userId2, UserDTO userData, boolean status) {
		this.userID = userId2;
		this.firstName = userData.getFirstName();
		this.lastName = userData.getLastName();
		this.email = userData.getEmail();
		this.address = userData.getAddress();
		this.phoneNumber = userData.getPhoneNumber();
		this.dob = userData.getDob();
		this.password = userData.getPassword();
		this.tokenStatus = status;
	}
}
