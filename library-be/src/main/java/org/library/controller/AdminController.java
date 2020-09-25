package org.library.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.library.exception.LibraryException;
import org.library.service.MetaDataService;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value = "fetches all metadata", response = Map.class)
    public Map<String, String> getAllMetaData() throws LibraryException {
        return metaDataService.getAllMetadata();
    }

    @PostMapping("/metadata")
    @ApiOperation(value = "updates the metadata passed (if key already exists, else inserts)", response = String.class)
    public String persistMetaData(@RequestBody Map<String, String> map) throws LibraryException {
        metaDataService.persistMetadata(map);
        return ("[]");
    }
}
