package pal.sourav.bookstoreapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

	@NotEmpty(message = "Please provide email id")
	@Email
	private String email;
	
	
	@NotEmpty(message = "Please provide password")
	private String password;
}
