package org.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.library.controller.dto.BookDTO;
import org.library.exception.LibraryException;
import org.library.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdminService adminService;

    private Map<String, String> metaData = new LinkedHashMap<>();
    private BookDTO book1;
    private BookDTO book2;

    @Before
    public void setup() throws LibraryException {

        metaData.put("key-1", "val-1");
        metaData.put("key-2", "val-2");
        metaData.put("key-3", "val-3");

        book1 = BookDTO.builder().id(10L).bookName("Alice in Wonderland").author("Rudyard Kipling").
                description("Young Adults").quantity(120).availability(120).build();
        book2 = BookDTO.builder().id(20L).bookName("Scandal in Bohemia").author("Sir Arthur Conan Doyle").
                description("Sherlock Holmes").quantity(50).availability(50).build();

        given(adminService.getAllMetadata()).willReturn(metaData);
        doNothing().when(adminService).persistMetadata(isNotNull());

        doReturn(10L).when(adminService).addBookToRepo(book1);
        doReturn(20L).when(adminService).addBookToRepo(book1);
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
                .andExpect(content().json("{}"));
    }

    @Test
    public void testGetAllMetaDataError() throws Exception {
        given(adminService.getAllMetadata()).willThrow(new RuntimeException("No metadata found"));

        mvc.perform(get("/admin/metadata")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("No metadata found")))
                .andExpect(jsonPath("$.status", is("Internal Server Error")));
    }

    @Test
    public void testPersistMetaDataError() throws Exception {
        doThrow(new LibraryException("Failed persistence")).when(adminService).persistMetadata(isNotNull());

        mvc.perform(post("/admin/metadata", metaData)
                .content(new ObjectMapper().writeValueAsString(metaData))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Failed persistence")))
                .andExpect(jsonPath("$.status", is("Internal Server Error")));
    }

    @Test
    public void testPersistBook() throws Exception {
        mvc.perform(post("/admin/book")
                .content(new ObjectMapper().writeValueAsString(book1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    public void testModifyBook() throws Exception {
        mvc.perform(put("/admin/book")
                .content(new ObjectMapper().writeValueAsString(Arrays.asList(book1, book2)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }
}
