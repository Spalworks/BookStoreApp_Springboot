package pal.sourav.bookstoreapp.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import pal.sourav.bookstoreapp.dto.ChangePasswordDTO;
import pal.sourav.bookstoreapp.dto.LoginDTO;
import pal.sourav.bookstoreapp.dto.UserDTO;
import pal.sourav.bookstoreapp.entity.User;
import pal.sourav.bookstoreapp.exceptions.BookStoreException;
import pal.sourav.bookstoreapp.repository.UserRepository;
import pal.sourav.bookstoreapp.util.EmailService;
import pal.sourav.bookstoreapp.util.TokenUtil;


@Service
@Slf4j
public class UserService implements IUserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TokenUtil tokenUtil;
	
	@Autowired
	EmailService emailService;
	
	
	//get all the user details present in the Bookstore
	@Override
	public List<User> getAlluser() {
		List<User> response = userRepository.findAll();
		if (response.size() == 0)
			throw new BookStoreException("No user found!!!");

		return response;
	}

	
	//getting data of a particular user by using the ID of that user
	@Override
	public User getUserById(int userId) {
		Optional<User> response = userRepository.findById(userId);
		return response.orElseThrow(() -> new BookStoreException("User not found with this userId : " + userId));
	}

	
	//getting data of a particular user by using the emailID of that user
	@Override
	public User getUserByEmail(String emailId) {
		Optional<User> response = userRepository.findByEmail(emailId);
		return response.orElseThrow(() -> new BookStoreException("User not found with this email : " + emailId));
	}

	
	
	//creating a new user by sending the datas in the Body
	@Override
	public User createNewUser(UserDTO dto) {
		User user = new User(dto);
		userRepository.save(user);
		
		log.info("User created with id: " + user.getUserID());
		String token = tokenUtil.createToken(user.getUserID());

		emailService.sendEmail(dto.getEmail(), "Sign-up BookStore",
				"Hi " + dto.getFirstName() + " " + dto.getLastName() + " !!!" + "\n\n"
						+ "Welcome to BookStore App of Sourav Pal." + "\n"
						+ "Account is created successfully. To verify the account please click on the link below: "
						+ "\n" + "http://localhost:8081/addressbookservice/verify-user-by-token/" + token +"\n\n"
						+ "Token: " + token);
		
		log.info(token);
		return user;
	}

	@Override
	public User userLogin(@Valid LoginDTO loginDto) {
		User existingUser = getUserByEmail(loginDto.getEmail());
		
		if(existingUser.isTokenStatus()) {   //if the user is verified then only we'll go further

			if (loginDto.getPassword().equals(existingUser.getPassword())) {
				
				String token = tokenUtil.createToken(existingUser.getUserID());
				emailService.sendEmail(loginDto.getEmail(), "Sign-in BookStore",
						"Hi " + existingUser.getFirstName() + " " + existingUser.getLastName() + " !!!" + "\n\n"
						+ " Your Account has been Loggin Successfully.Your token is " + token);
				log.info("SuccessFully Logged In");
				
				return existingUser;
			}else {
				log.info("Password is not matching");
				throw new BookStoreException("Password is not matching.");
			}
			
		}else
			throw new BookStoreException("User is not verified");
	}

	@Override
	public User updateUserDataByEmail(String email, int userId, UserDTO dto) {
		User existingUser = getUserByEmail(dto.getEmail());
		
		if (existingUser != null) {
			existingUser = new User(userId, dto);
			existingUser.setTokenStatus(true);
			userRepository.save(existingUser);
			
			String newToken = tokenUtil.createToken(existingUser.getUserID());
	
			emailService.sendEmail(dto.getEmail(), "Update User Credentials",
					"Hi " + existingUser.getFirstName() + " " + existingUser.getLastName() + " !!!" + "\n\n"
							+ "User data is updated successfully. To check the User data please click on the link below: "
							+ "\n" + "http://localhost:8080/addressbookservice/get-user-by-token/" + newToken);	
		}
		
		return existingUser;
	}

	@Override
	public User resetPassword(ChangePasswordDTO dto) {
		User existingUser = getUserByEmail(dto.getEmail());
		
		if (dto.getEmail().equals(existingUser.getEmail())) {
			String newPassword = dto.getNewPassword();
			log.info("new password :" + newPassword);
			existingUser.setPassword(newPassword);
			log.info("Setting new password \n New password : " + existingUser.getPassword());
			existingUser.setTokenStatus(true);
			userRepository.save(existingUser);
			log.info("Password changes successfully");
			
			
			emailService.sendEmail(existingUser.getEmail(), "Update Password",
					"Hi " + existingUser.getFirstName() + " " + existingUser.getLastName() + " !!!" + "\n\n"
							+ "This is to inform you that your account password has been updated successfully." + "\n"
							+ "Your new Password is :" + existingUser.getPassword());

			return existingUser;
		} else {
			throw new BookStoreException("Invalid token");
		}
	}

	@Override
	public int deleteUserByToken(String token) {
		int userId = tokenUtil.decodeToken(token);
		Optional<User> existUser = userRepository.findById(userId);
		if (existUser.isPresent()) {
			userRepository.deleteById(userId);
		} else
			throw new BookStoreException("User not found!!!");

		return userId;
	}

	@Override
	public User getUserDataByToken(@Valid String token) {
		int userId = tokenUtil.decodeToken(token);
		log.info("Id from token: " + userId);
		Optional<User> response = userRepository.findById(userId);
		return response.orElseThrow(() -> new BookStoreException("User not found with token : " + token));
	}

	@Override
	public User verifyUserbyToken(@Valid String token) {
		int userId = tokenUtil.decodeToken(token);
		Optional<User> response = userRepository.findById(userId);
		
		if (response.isPresent()) {	
			response.get().setTokenStatus(true);  //Setting tokenStatus as 'true' when verifying the user
			userRepository.save(response.get());  //after updating token status we're saving it to db
			log.info("User is verified");
		}
		
		return response.orElseThrow(() -> new BookStoreException("User not found with token : " + token));
	}


	
}
