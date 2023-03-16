package com.javamentor.qa.platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.junit5.api.DBRider;
import com.javamentor.qa.platform.models.dto.AuthenticationRequestDto;
import com.javamentor.qa.platform.models.dto.TokenResponseDto;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
@Transactional
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
public abstract class BaseTest {

	@Autowired
	protected MockMvc mockMvc;

	protected final ObjectMapper objectMapper = new ObjectMapper();

	public String getToken(String username, String password) throws Exception {
		AuthenticationRequestDto requestDto = new AuthenticationRequestDto();
		requestDto.setLogin(username);
		requestDto.setPass(password);

		MvcResult result = mockMvc
			.perform(post("/api/auth/token")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(requestDto)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
		String responseBody = result.getResponse().getContentAsString();
		TokenResponseDto tokenResponseDto = objectMapper.readValue(responseBody, TokenResponseDto.class);
		return "Bearer " + tokenResponseDto.getToken();
	}
}
