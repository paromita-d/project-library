package org.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.library.exception.LibraryException;
import org.library.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private MetaDataService service;

    private Map<String, String> metaData = new LinkedHashMap<>();

    @Before
    public void setup() throws LibraryException {

        metaData.put("key-1", "val-1");
        metaData.put("key-2", "val-2");
        metaData.put("key-3", "val-3");

        given(service.getAllMetadata()).willReturn(metaData);
        doNothing().when(service).persistMetadata(isNotNull());
    }

    @Test
    public void testGetAllMetaData() throws Exception {
        mvc.perform(get("/admin/metadata")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"key-1\":\"val-1\",\"key-2\":\"val-2\",\"key-3\":\"val-3\"}"))
                .andExpect(jsonPath("$.key-1", is("val-1")))
                .andExpect(jsonPath("$.key-2", is("val-2")))
                .andExpect(jsonPath("$.key-3", is("val-3")));
    }

    @Test
    public void testPersistMetaData() throws Exception {
        mvc.perform(post("/admin/metadata")
                .content(new ObjectMapper().writeValueAsString(metaData))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testGetAllMetaDataError() throws Exception {
        given(service.getAllMetadata()).willThrow(new LibraryException("No metadata found"));

        mvc.perform(get("/admin/metadata")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errorBody", is("No metadata found")))
                .andExpect(jsonPath("$.httpStatus", is("Internal Server Error")));
    }

    @Test
    public void testPersistMetaDataError() throws Exception {
        doThrow(new LibraryException("Failed persistence")).when(service).persistMetadata(isNotNull());

        mvc.perform(post("/admin/metadata", metaData)
                .content(new ObjectMapper().writeValueAsString(metaData))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errorBody", is("Failed persistence")))
                .andExpect(jsonPath("$.httpStatus", is("Internal Server Error")));
    }
}
