package com.github.ngj1129.fileextensionblock.medium.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.github.ngj1129.fileextensionblock.extension.service.ExtensionBlockService;
import com.github.ngj1129.fileextensionblock.extension.web.ExtensionBlockController;

@WebMvcTest(controllers = ExtensionBlockController.class)
class CustomExtensionRequestTest {

	@Autowired
	MockMvc mockMvc;

	@MockitoBean
	ExtensionBlockService extensionBlockService;

	@Test
	@DisplayName("확장자 빈 값이면 400")
	void ext_blank_returns400() throws Exception {
		mockMvc.perform(post("/api/extensions/custom")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{"ext": "   "}
				"""))
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("확장자 null이면 400")
	void ext_null_returns400() throws Exception {
		mockMvc.perform(post("/api/extensions/custom")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{"ext": null}
				"""))
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("확장자 20자 초과면 400")
	void ext_over20_returns400() throws Exception {
		mockMvc.perform(post("/api/extensions/custom")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{"ext": "abcdefghijklmnopqrstu"}
				"""))
			.andExpect(status().isBadRequest());
	}
}
