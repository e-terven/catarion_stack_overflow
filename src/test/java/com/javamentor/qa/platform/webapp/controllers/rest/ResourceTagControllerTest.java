package com.javamentor.qa.platform.webapp.controllers.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.DataSetFormat;
import com.github.database.rider.core.api.exporter.ExportDataSet;
import com.javamentor.qa.platform.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class ResourceTagControllerTest extends BaseTest {
  @Autowired
  private MockMvc mockMvc;

  private final String USERNAME = "test@ya.ru";
  private final String PASSWORD = "test";
  @Test
  @DataSet(value = "datasets/resource_tag_controller/tag.yml", cleanBefore = true, cleanAfter = true, disableConstraints = true)
  @ExportDataSet(format = DataSetFormat.YML,outputName="target/exported/yml/allTables.yml")
  void getAllIgnoredTag() throws Exception{
    mockMvc.perform(get("/api/user/tag/ignored")
        .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + getToken(USERNAME, PASSWORD)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(equalTo(10))))
        .andExpect(jsonPath("$.ignoredtagid", is(equalTo(1))))
        .andExpect(jsonPath("$.userid", is(equalTo(100))))
        .andExpect(jsonPath("$.persistdate", is(equalTo("2019-01-01"))));
  }
}