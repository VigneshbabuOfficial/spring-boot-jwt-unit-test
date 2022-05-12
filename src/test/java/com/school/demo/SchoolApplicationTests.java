package com.school.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.school.utils.CommonConstants;
import com.school.utils.SchoolLogger;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SchoolApplicationTests {

	private static final String LOG_STR = "StudentsControllerTest.%s";

	@Autowired
	private SchoolLogger logger;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private String usersToken;

	@Test
	@Order(1)
	public void getAllStudentsTest() throws Exception {

		logger.info(String.format(LOG_STR, "getAllStudentsTest"));

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SecurityMockMvcConfigurers.springSecurity()).build();

		ResultActions mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/students").header("Authorization",
						String.format("Bearer %s", usersToken)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

		logger.info(String.format(LOG_STR, "getAllStudentsTest") + ", Response = "
				+ mvcResult.andReturn().getResponse().getContentAsString());

		JsonNode responseObje = new ObjectMapper().readTree(mvcResult.andReturn().getResponse().getContentAsString());

		assertTrue(responseObje.has(CommonConstants.RESPONSE_STR));

		assertTrue(responseObje.has(CommonConstants.DATA));

		assertTrue(responseObje.get(CommonConstants.DATA) instanceof ArrayNode);

		assertEquals(CommonConstants.SUCCESS_LOWER_STR, responseObje.get(CommonConstants.RESPONSE_STR).asText());

		assertEquals(200, mvcResult.andReturn().getResponse().getStatus());

	}

	@Test
	@Order(2)
	public void getAllStudentsTest1() throws Exception {

		logger.info(String.format(LOG_STR, "getAllStudentsTest1"));

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SecurityMockMvcConfigurers.springSecurity()).build();

		ResultActions mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/students").header("Authorization",
						String.format("Bearer %s", usersToken)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

		logger.info(String.format(LOG_STR, "getAllStudentsTest1") + ", Response = "
				+ mvcResult.andReturn().getResponse().getContentAsString());

		JsonNode responseObje = new ObjectMapper().readTree(mvcResult.andReturn().getResponse().getContentAsString());

		assertTrue(responseObje.has(CommonConstants.RESPONSE_STR));

		assertTrue(responseObje.has(CommonConstants.DATA));

		assertTrue(responseObje.get(CommonConstants.DATA) instanceof ArrayNode);

		assertEquals(CommonConstants.SUCCESS_LOWER_STR, responseObje.get(CommonConstants.RESPONSE_STR).asText());

		assertEquals(200, mvcResult.andReturn().getResponse().getStatus());

	}

}
