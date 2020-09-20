package org.library.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.library.dto.MetaData;
import org.library.exception.LibraryException;
import org.library.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private MetaDataService service;

    private List<MetaData> metaData = new ArrayList<>();

    @Before
    public void setup() throws LibraryException {

        metaData.add(MetaData.builder().metaKey("key-1").metaValue("val-1").build());
        metaData.add(MetaData.builder().metaKey("key-2").metaValue("val-2").build());
        metaData.add(MetaData.builder().metaKey("key-3").metaValue("val-3").build());

        given(service.getAllMetadata()).willReturn(metaData);
    }

    @Test
    public void testGetAllMetaData() throws Exception {
        mvc.perform(get("/metadata")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(metaData.size())))
                .andExpect(jsonPath("$[0].metaKey", is(metaData.get(0).getMetaKey())));
    }

    @Test
    public void testGetAllMetaDataError() throws Exception {
        given(service.getAllMetadata()).willThrow(new LibraryException("No metadata found"));

        mvc.perform(get("/metadata")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errorBody", is("No metadata found")))
                .andExpect(jsonPath("$.httpStatus", is("Internal Server Error")));
    }
}
