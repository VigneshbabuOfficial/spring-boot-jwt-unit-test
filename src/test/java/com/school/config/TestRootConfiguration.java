package com.school.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.school.entities.Users;
import com.school.repos.UserRepository;

@Configuration
public class TestRootConfiguration {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private UserRepository userRepository;

	@Bean(name = "usersToken")
	public String getAccessToken() {

		try {
			
			
			Users user =  userRepository.findOne();

			ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
			objectNode.put("username", user.getUserName());
			objectNode.put("password", user.getPassword());

			System.out.println(" ############### objectNode - " + objectNode);

			MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
					.apply(SecurityMockMvcConfigurers.springSecurity()).build();

			ResultActions mvcResult = mockMvc
					.perform(MockMvcRequestBuilders.post("/authenticate/login").content(objectNode.toString())
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

			JsonNode responseObje = new ObjectMapper()
					.readTree(mvcResult.andReturn().getResponse().getContentAsString());

			assertTrue(responseObje.has("token"));

			assertEquals(200, mvcResult.andReturn().getResponse().getStatus());

			System.out.println(" ######## responseObje - " + responseObje);

			return responseObje.get("token").asText();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
