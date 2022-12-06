package com.stackroute.keepnote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.dao.UserDAO;
import com.stackroute.keepnote.exception.UserAlreadyExistException;
import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn�t currently 
* provide any additional behavior over the @Component annotation, but it�s a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class UserServiceImpl implements UserService {

	/*
	 * Autowiring should be implemented for the userDAO. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword.
	 */
	UserDAO userDAO;
	
	public UserServiceImpl() {
		super();
	}
	
	@Autowired
	public UserServiceImpl(UserDAO userDAO) {
		this.userDAO=userDAO;
	}

	/*
	 * This method should be used to save a new user.
	 */

	public boolean registerUser(User user) throws UserAlreadyExistException {
		if( userDAO.registerUser(user)) {
			return Boolean.TRUE;			
		}else {
			throw new UserAlreadyExistException(user.getUserId()+ "already exist");
		}
	}

	/*
	 * This method should be used to update a existing user.
	 */

	public User updateUser(User user, String userId) throws Exception {
		userDAO.updateUser(user);
		User u= userDAO.getUserById(userId);
		if(u ==null) {
			throw new Exception();
		}
		return u;
	}

	/*
	 * This method should be used to get a user by userId.
	 */

	public User getUserById(String userId) throws UserNotFoundException {
		User user = userDAO.getUserById(userId);
		if(user==null) {
			throw new UserNotFoundException(userId+" Not found");
		}
		return user;
	}

	/*
	 * This method should be used to validate a user using userId and password.
	 */

	public boolean validateUser(String userId, String password) throws UserNotFoundException {
		boolean userExist=userDAO.validateUser(userId, password);
		if(!userExist)
			throw new UserNotFoundException("UserNotFoundException");
		return userExist;
	}

	/* This method should be used to delete an existing user. */
	public boolean deleteUser(String UserId) {
		return userDAO.deleteUser(UserId);
	}

}