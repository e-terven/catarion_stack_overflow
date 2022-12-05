package com.javamentor.qa.platform.controllers;

import com.javamentor.qa.platform.webapp.controllers.rest.ResourceTagController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ResourceTagController.class)
class ResourceTagControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void addTagToTrackedTag() throws Exception {
        mockMvc.perform(get("/api/user/tag/1/tracked")
                .contentType(MediaType.APPLICATION_JSON)
                .header("H","1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}