package pal.sourav.bookstoreapp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChangePasswordDTO {
	
	@NotEmpty(message = "Please provide email id")
	private String email;
	
	
	@NotEmpty(message = "Please provide password")
	private String newPassword;
}
