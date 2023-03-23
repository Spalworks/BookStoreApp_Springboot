package pal.sourav.bookstoreapp.controller;

import java.util.List;

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

import jakarta.validation.Valid;
import pal.sourav.bookstoreapp.dto.ChangePasswordDTO;
import pal.sourav.bookstoreapp.dto.LoginDTO;
import pal.sourav.bookstoreapp.dto.ResponseDTO;
import pal.sourav.bookstoreapp.dto.UserDTO;
import pal.sourav.bookstoreapp.entity.User;
import pal.sourav.bookstoreapp.service.IUserService;


@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	IUserService userService;

	
	@GetMapping(value = {"", "/", "/getall"})
	public ResponseEntity<ResponseDTO> getAllUser(){
		List<User> result = userService.getAlluser();
		ResponseDTO responseDTO = new ResponseDTO(result, "User Data retrieved successfully");
		
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.FOUND);
	}
	
	
	@GetMapping(value = "/get-user-by-id/{userId}")
	public ResponseEntity<ResponseDTO> getUserById(@PathVariable int userId){
		User result = userService.getUserById(userId);
		ResponseDTO responseDTO = new ResponseDTO(result, "User Data retrieved successfully");
		
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.FOUND);
	}
	
	
	@GetMapping(value = "/get-user-by-email/{emailId}")
	public ResponseEntity<ResponseDTO> getUserByEmail(@Valid@PathVariable String emailId){
		User result = userService.getUserByEmail(emailId);
		ResponseDTO responseDTO = new ResponseDTO(result, "User Data retrieved successfully");
		
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.FOUND);
	}
	
	@GetMapping(value = "/get-user-by-token/{token}")
	public ResponseEntity<ResponseDTO> getUserByToken(String token){
		User result = userService.getUserDataByToken(token);
		ResponseDTO responseDTO = new ResponseDTO(result, "User Data retrieved successfully");
		
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.FOUND);
	}
	
	
	
	@GetMapping(value = "/verify-user-by-token/{token}")
	public ResponseEntity<ResponseDTO> verifyUserbyToken(@Valid@PathVariable String token){
		User result = userService.verifyUserbyToken(token);
		ResponseDTO responseDTO = new ResponseDTO(result, "Data retrived Successfully using token");
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.FOUND);
	}
	
	
	@PostMapping(value = "/register")
	public ResponseEntity<ResponseDTO> createUser(@RequestBody UserDTO userData){
		User result = userService.createNewUser(userData);
		ResponseDTO responseDTO = new ResponseDTO(result, "User data created successfully");
		
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.CREATED);
	}
	
	
	@PostMapping(value = "/login")
	public ResponseEntity<ResponseDTO> userLogin(@RequestBody LoginDTO loginDto){
		User result = userService.userLogin(loginDto);
		ResponseDTO responseDTO = new ResponseDTO(result, "Congradulations!!! You've logged in successfully.");
		
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	
	
	
	@PutMapping(value = "/update-user-by-email/{email}/{userId}")
	public ResponseEntity<ResponseDTO> updateUserdata(@PathVariable String email, @PathVariable int userId, @RequestBody UserDTO userData){
		User result = userService.updateUserDataByEmail(email, userId, userData);
		ResponseDTO responseDTO = new ResponseDTO(result, "UserData updated successfully");
		
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	
	@PutMapping(value = "/updatePassword/{userId}")
	public ResponseEntity<ResponseDTO> changePassword(@RequestBody ChangePasswordDTO dto){
		User result = userService.resetPassword(dto);
		ResponseDTO responseDTO = new ResponseDTO(result, "Password updated successfully");
		
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	
	
	
	@DeleteMapping(value = "/delete-by-token/{token}")
	public ResponseEntity<ResponseDTO> deleteUserByToken(@PathVariable String token){
		Integer result = userService.deleteUserByToken(token);
		ResponseDTO responseDTO = new ResponseDTO(result, "UserData deleted successfully");
		
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	
	
}
