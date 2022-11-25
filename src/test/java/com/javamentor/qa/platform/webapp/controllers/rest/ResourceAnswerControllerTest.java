package com.javamentor.qa.platform.webapp.controllers.rest;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.DataSetFormat;
import com.github.database.rider.core.api.exporter.ExportDataSet;
import com.javamentor.qa.platform.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


class ResourceAnswerControllerTest extends BaseTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  @DataSet(value = "datasets/answer_controller/answer.yml", cleanBefore = true, cleanAfter = true, disableConstraints = true)
  @ExportDataSet(format = DataSetFormat.YML,outputName="target/exported/yml/allTables.yml")
  public void getAllAnswer() throws Exception {

    mockMvc.perform(get("/api/user/question/2/answer")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Header","123"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(equalTo(1))))
        .andExpect(jsonPath("$.isdeleted", is(equalTo(false))))
        .andExpect(jsonPath("$.isdeletedbymoderator", is(equalTo(false))))
        .andExpect(jsonPath("$.ishelpful", is(equalTo(false))))
        .andExpect(jsonPath("$.htmlbody", is(equalTo("This is a test answer"))))
        .andExpect(jsonPath("$.updatedate", is(equalTo("2019-01-01T00:00:00"))))
        .andExpect(jsonPath("$.user_id", is(equalTo(100))))
        .andExpect(jsonPath("$.question_id", is(equalTo(1))))
        .andExpect(jsonPath("$.id", is(equalTo(2))))
        .andExpect(jsonPath("$.isdeleted", is(equalTo(false))))
        .andExpect(jsonPath("$.isdeletedbymoderator", is(equalTo(false))))
        .andExpect(jsonPath("$.ishelpful", is(equalTo(false))))
        .andExpect(jsonPath("$.htmlbody", is(equalTo("This is a test answer"))))
        .andExpect(jsonPath("$.updatedate", is(equalTo("2019-01-01T00:00:00"))))
        .andExpect(jsonPath("$.user_id", is(equalTo(100))))
        .andExpect(jsonPath("$.question_id", is(equalTo(2))));
  }
}
