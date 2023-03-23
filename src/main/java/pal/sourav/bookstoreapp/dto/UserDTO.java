package pal.sourav.bookstoreapp.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	@Pattern(regexp = "^[A-Z]{1}[A-Za-z]{2,}$", message = "Invalid FirstName")
	@NotEmpty(message = "FirstName should not be null")
	private String firstName;
	
	@Pattern(regexp = "^[A-Z]{1}[A-Za-z]{2,}$", message = "Invalid LastName")
	@NotEmpty(message = "LastName should not be null")
	private String lastName;
	
	@NotBlank(message = "Email should not be empty!")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
	private String email;
	
	@NotBlank(message = "Address should not be empty!")
	private String address;
	
	@NotNull
	@Pattern(regexp = "^[0-9]{10}$", message = "PhoneNumber should not be empty!")
	private String phoneNumber;
	
//	@NotEmpty(message = "Date of Birth should not be empty!")
//	@Pattern(regexp = "dd-MM-yyyy")
	private Date dob;
	
	@NotBlank(message = "password should not be empty!")
	private String password;
	
}
