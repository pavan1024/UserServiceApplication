package com.epam.exceptionhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.epam.dto.UserDto;

import com.epam.entity.User;
import com.epam.exception.NoUsersException;
import com.epam.exception.UserNotFoundException;
import com.epam.repo.UserRepository;
import com.epam.service.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@AutoConfigureMockMvc
@SpringBootTest
class RestExceptionHandlerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	@MockBean
	UserService userService;
	
	@MockBean
	UserRepository userRepository;
	
	
	UserDto userDto;
	User user;
	
	protected <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException,JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
	
	@BeforeEach
	void setUp() {
		userDto = new UserDto();
		userDto.setUsername("username");
		userDto.setEmail("email123@gmail.com");
		
		user = new User();
		userDto.setUsername("username1");
		userDto.setEmail("email321@gmail.com");
	}
	
	@Test
	void handlerUserNotFoundExceptionTest() throws Exception {
		when(userService.getUser("username")).thenThrow(new UserNotFoundException("User Not Found"));
		MvcResult result = mockMvc.perform(get("/users/username"))
				.andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		HashMap<String ,String> data = this.mapFromJson(response, HashMap.class);
		assertEquals("User Not Found",data.get("error"));
	}
	
	@Test
	void handlerNoUsersExceptionTest() throws Exception {
		when(userService.fetchAllUsers()).thenThrow(new NoUsersException("No Users"));
		MvcResult result = mockMvc.perform(get("/users/"))
				.andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		HashMap<String ,String> data = this.mapFromJson(response, HashMap.class);
		assertEquals("No Users",data.get("error"));
	}
	
	
}
