package pal.sourav.bookstoreapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	// get all the user details present in the Bookstore
	@Override
	public List<User> getAlluser() {
		List<User> response = userRepository.findAll();
		if (response.size() == 0)
			throw new BookStoreException("No user found!!!");

		return response;
	}

	// getting data of a particular user by using the ID of that user
	@Override
	public User getUserById(int userId) {
		Optional<User> response = userRepository.findById(userId);
		return response.orElseThrow(() -> new BookStoreException("User not found with this userId : " + userId));
	}

	// getting data of a particular user by using the emailID of that user
	@Override
	public User getUserByEmail(String emailId) {
		Optional<User> response = userRepository.findByEmail(emailId);
		return response.orElseThrow(() -> new BookStoreException("User not found with this email : " + emailId));
	}

	// creating a new user by sending the datas in the Body
	@Override
	public User createNewUser(UserDTO dto) {
		User user = new User(dto);
		userRepository.save(user);

		log.info("User created with id: " + user.getUserID());
		String token = tokenUtil.createToken(user.getUserID());

		emailService.sendEmail(dto.getEmail(), "Sign-up BookStore", "Hi " + dto.getFirstName() + " " + dto.getLastName()
				+ " !!!" + "\n\n" + "Welcome to BookStore App of Sourav Pal." + "\n"
				+ "Account is created successfully. To verify the account please click on the link below: " + "\n"
				+ "http://localhost:8081/user/verify-user-by-token/" + token + "\n\n" + "Token: " + token);

		log.info(token);
		return user;
	}

	// user log in(NOTE: If user is not verified i.e.tokenStatus=false then user
	// will not be able to login)
	@Override
	public User userLogin(LoginDTO loginDto) {
		User existingUser = getUserByEmail(loginDto.getEmail());

		if (existingUser.isTokenStatus()) { // if the user is verified then only we'll go further

			if (loginDto.getPassword().equals(existingUser.getPassword())) {

				String token = tokenUtil.createToken(existingUser.getUserID());
				emailService.sendEmail(loginDto.getEmail(), "Sign-in BookStore",
						"Hi " + existingUser.getFirstName() + " " + existingUser.getLastName() + " !!!" + "\n\n"
								+ " Your Account has been Loggin Successfully.Your token is " + token);
				log.info("SuccessFully Logged In");

				return existingUser;
			} else {
				log.info("Password is not matching");
				throw new BookStoreException("Password is not matching.");
			}

		} else
			throw new BookStoreException("User is not verified");
	}

	// getting user by email & then updating user data
	@Override
	public User updateUserDataByEmail(String email, UserDTO dto) {
		User existingUser = getUserByEmail(dto.getEmail());

		if (existingUser != null) {
			existingUser = new User(existingUser.getUserID(), dto, existingUser.isTokenStatus());
			userRepository.save(existingUser);

			String newToken = tokenUtil.createToken(existingUser.getUserID());

			emailService.sendEmail(dto.getEmail(), "Update User Credentials", "Hi " + existingUser.getFirstName() + " "
					+ existingUser.getLastName() + " !!!" + "\n\n"
					+ "User data is updated successfully. To check the User data please click on the link below: "
					+ "\n" + "http://localhost:8080/user/get-user-by-token/" + newToken);
		}

		return existingUser;
	}

	// getting user by user ID & then updating user data
	@Override
	public User updateUserDataById(int userId, UserDTO dto) {
		Optional<User> existingUser = userRepository.findById(userId);

		if (existingUser.isPresent()) {
			User userData = new User(existingUser.get().getUserID(), dto, existingUser.get().isTokenStatus());
			userRepository.save(userData);

			String newToken = tokenUtil.createToken(userData.getUserID());

			emailService.sendEmail(userData.getEmail(), "Update Profile", "Hi " + userData.getFirstName() + " "
					+ userData.getLastName() + " !!!" + "\n\n"
					+ "User data is updated successfully. To check the User data please click on the link below: "
					+ "\n" + "http://localhost:8080/user/get-user-by-token/" + newToken);

			return userData;
		} else
			throw new BookStoreException("User not found with the id: " + userId);

	}

	/* Reset/change the password */
	@Override
	public User resetPassword(ChangePasswordDTO dto) {
		User existingUser = getUserByEmail(dto.getEmail());

		if (dto.getEmail().equals(existingUser.getEmail())) {
			String newPassword = dto.getNewPassword();
			existingUser.setPassword(newPassword);

			userRepository.save(existingUser);

			emailService.sendEmail(existingUser.getEmail(), "Update Password",
					"Hi " + existingUser.getFirstName() + " " + existingUser.getLastName() + " !!!" + "\n\n"
							+ "This is to inform you that your account password has been updated successfully." + "\n"
							+ "Your new Password is :" + existingUser.getPassword());

			return existingUser;
		} else {
			throw new BookStoreException("Invalid token");
		}
	}

	// Identify the user with token & then delete the user from db
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

	// Getting user details using token
	@Override
	public User getUserDataByToken(String token) {
		int userId = tokenUtil.decodeToken(token);
		Optional<User> response = userRepository.findById(userId);
		return response.orElseThrow(() -> new BookStoreException("User not found with token : " + token));
	}

	// Verifying user(tokenStatus=true) using token
	@Override
	public User verifyUserbyToken(String token) {
		int userId = tokenUtil.decodeToken(token);
		Optional<User> response = userRepository.findById(userId);

		if (response.isPresent()) {
			response.get().setTokenStatus(true); // Setting tokenStatus as 'true' when verifying the user
			userRepository.save(response.get()); // after updating token status we're saving it to db
			log.info("User is verified");
		}

		return response.orElseThrow(() -> new BookStoreException("User not found with token : " + token));
	}

}
