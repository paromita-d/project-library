package org.library.controller;

import org.library.exception.LibraryException;
import org.library.service.MetaDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AdminController {

    private MetaDataService metaDataService;

    public AdminController(MetaDataService metaDataService) {
        this.metaDataService = metaDataService;
    }

    @GetMapping("/metadata")
    public Map<String, String> getAllMetaData() throws LibraryException {
        return metaDataService.getAllMetadata();
    }

    @PostMapping("/metadata")
    public String persistMetaData(@RequestBody Map<String, String> map) throws LibraryException {
        metaDataService.persistMetadata(map);
        return ("[]");
    }
}
