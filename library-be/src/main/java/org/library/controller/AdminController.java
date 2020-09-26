package org.library.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.library.controller.dto.BookDTO;
import org.library.controller.dto.UserDTO;
import org.library.exception.LibraryException;
import org.library.service.MetaDataService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@Api("endpoints for library admin")
public class AdminController {

    private MetaDataService metaDataService;

    /*
    * @Autowired is used to inject the object. If we want to create an instance of class A and inject and object of class B.
    Eg:
    @Autowired
    private MetaDataService metaDataService;
    // and there would have been no constructor as below

    * Instead of @Autowired, in the constructor of A pass an instance of B
    * As shown below
    * */


    public AdminController(MetaDataService metaDataService) {
        this.metaDataService = metaDataService;
    }

    @GetMapping("/metadata")
    @ApiOperation("fetches all metadata")
    public Map<String, String> getAllMetaData() throws LibraryException {
        return metaDataService.getAllMetadata();
    }

    @PostMapping("/metadata")
    @ApiOperation("updates the metadata passed (if key already exists, else inserts). Returns [] on success")
    public String persistMetaData(@RequestBody Map<String, String> map) throws LibraryException {
        metaDataService.persistMetadata(map);
        return ("[]");
    }

    @GetMapping("/overdue")
    @ApiOperation("fetches those users who are overdue")
    public List<UserDTO> getOverdue() {
        //todo add logic
        return Arrays.asList(UserDTO.builder().id(10L).userName("Apple").build(), UserDTO.builder().id(20L).userName("Windows").build());
    }

    @PostMapping("/book")
    @ApiOperation("add a new book to the library and return book id")
    public Long persistBook(@RequestBody BookDTO bookDTO) {
        //todo add logic
        return 10L;
    }

    @PutMapping("/book")
    @ApiOperation("update the quantity of books in inventory. Setting to 0 means removing these books. Count can not be negative. Returns [] on success")
    public String updateBooksQty(@RequestBody List<BookDTO> booksDTO) {
        //todo add logic
        return ("[]");
    }

}
