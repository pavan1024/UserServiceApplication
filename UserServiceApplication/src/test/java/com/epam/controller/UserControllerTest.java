package com.epam.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;

import com.epam.dto.UserDto;
import com.epam.entity.User;
import com.epam.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	UserService userService;

	ObjectMapper mapper;
	UserDto userDto;

	@BeforeEach
	void setUp() {
		mapper = new ObjectMapper();
		userDto = new UserDto();
		userDto.setUsername("username");
		userDto.setEmail("email123@gmail.com");

	}

	@Test
	void addUserTest() throws Exception {
		when(userService.addUser(any())).thenReturn(userDto);
		mockMvc.perform(post("/users/").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(userDto)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

	}

	@Test
	void deleteUserTest() throws Exception {
		when(userService.deleteUser("username")).thenReturn("User Deleted Successfully");
		MvcResult result = mockMvc.perform(delete("/users/username")).andExpect(status().isNoContent()).andReturn();
		String response = result.getResponse().getContentAsString();
		assertEquals("User Deleted Successfully", response);
	}

	@Test
	void getUserTest() throws Exception {
		User user = new User();
		user.setUsername("username");
		when(userService.getUser("username")).thenReturn(user);

		MvcResult result = mockMvc.perform(get("/users/username")).andExpect(status().isOk()).andReturn();
		int statusCode = result.getResponse().getStatus();

		assertEquals(200, statusCode);
	}

	@Test
	void fetchAllUsersTest() throws Exception {
		List<User> users = new ArrayList<>();
		User user = new User();
		userDto.setUsername("username");
		userDto.setEmail("email123@gmail.com");
		users.add(user);

		when(userService.fetchAllUsers()).thenReturn(users);
		mockMvc.perform(get("/users")).andExpect(status().isOk()).andReturn();
	}

	@Test
	void updateUserTest() throws Exception {
		when(userService.updateUser("user",userDto)).thenReturn(userDto);
		mockMvc.perform(put("/users/user").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(userDto)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted()).andReturn();
	}

}
