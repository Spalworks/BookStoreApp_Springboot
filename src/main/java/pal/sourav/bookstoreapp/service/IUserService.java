package pal.sourav.bookstoreapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pal.sourav.bookstoreapp.dto.ChangePasswordDTO;
import pal.sourav.bookstoreapp.dto.LoginDTO;
import pal.sourav.bookstoreapp.dto.UserDTO;
import pal.sourav.bookstoreapp.entity.User;


@Service
public interface IUserService {

	List<User> getAlluser();

	User getUserById(int userId);

	User getUserByEmail(String emailId);

	User createNewUser(UserDTO userData);
	
	User userLogin(LoginDTO dto);

	User updateUserDataByEmail(String email, UserDTO userData);
	
	User updateUserDataById(int userId, UserDTO userData);
	
	User resetPassword(ChangePasswordDTO dto);

	int deleteUserByToken(String token);

	User getUserDataByToken(String token);

	User verifyUserbyToken(String token);

	


}
