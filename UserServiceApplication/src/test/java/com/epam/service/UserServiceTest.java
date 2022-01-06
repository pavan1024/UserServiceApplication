package com.epam.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.dto.UserDto;
import com.epam.entity.User;
import com.epam.exception.UserAlreadyExistsException;
import com.epam.exception.UserNotFoundException;
import com.epam.repo.UserRepository;

@SpringBootTest
class UserServiceTest {

	@Mock
	UserRepository userRepository;

	@Mock
	ModelMapper mapper;

	@InjectMocks
	UserService userService;

	User user;
	UserDto userDto;
	List<User> users;

	@BeforeEach
	void setUp() {
		user = new User();
		user.setUsername("username");
		user.setEmail("email123@gmail.com");

		userDto = new UserDto();
		userDto.setUsername("newusername");
		userDto.setEmail("newemail123@gmail.com");

		users = new ArrayList<>();
		users.add(user);
	}

	@Test
	void addUserTest() {
		Optional<User> optionalUser = Optional.empty();
		when(userRepository.findById(user.getUsername())).thenReturn(optionalUser);
		when(mapper.map(userDto, User.class)).thenReturn(user);
		when(mapper.map(user, UserDto.class)).thenReturn(userDto);

		assertEquals(userDto, userService.addUser(userDto));
	}

	@Test
	void addUserErrorTest() {
		Optional<User> optionalUser = Optional.ofNullable(user);
		when(userRepository.findById(userDto.getUsername())).thenReturn(optionalUser);
		Throwable exception = assertThrows(UserAlreadyExistsException.class, () -> userService.addUser(userDto));
		assertEquals("User Already Exists", exception.getMessage());
	}

	@Test
	void deleteUserTest() {
		Optional<User> optionalBook = Optional.ofNullable(user);
		when(userRepository.findById(user.getUsername())).thenReturn(optionalBook);
		assertEquals("User Deleted Successfully", userService.deleteUser(user.getUsername()));
	}

	@Test
	void deleteUserErrorTest() {
		Throwable exception = assertThrows(UserNotFoundException.class,
				() -> userService.deleteUser(user.getUsername()));
		assertEquals("User Not Found", exception.getMessage());
	}

	@Test
	void getUserTest() {
		User user1 = new User();
		user1.setUsername("newuser");
		user1.setEmail("newmail09@gmail.com");
		Optional<User> optionalBook = Optional.ofNullable(user1);
		when(userRepository.findById(user1.getEmail())).thenReturn(optionalBook);
		assertEquals(user1, userService.getUser(user1.getEmail()));
	}

	@Test
	void getUserErrorTest() {
		Throwable exception = assertThrows(UserNotFoundException.class,
				() -> userService.getUser((user.getUsername())));
		assertEquals("User Not Found", exception.getMessage());
	}

	@Test
	void getAllBooksTest() {
		when(userRepository.findAll()).thenReturn(users);
		assertEquals(users, userService.fetchAllUsers());
	}

	@Test
	void updatebookTest() {
		Optional<User> optionalBook = Optional.ofNullable(user);
		when(mapper.map(user, UserDto.class)).thenReturn(userDto);
		when(userRepository.findById("user")).thenReturn(optionalBook);
		assertEquals(userDto, userService.updateUser("user", userDto));
	}

	@Test
	void updatebookErrorTest() {
		Throwable exception = assertThrows(UserNotFoundException.class, () -> userService.updateUser("user", userDto));
		assertEquals("User Not Found", exception.getMessage());
	}

}
