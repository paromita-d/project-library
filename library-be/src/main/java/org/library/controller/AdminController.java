package org.library.controller;

import org.library.dto.MetaData;
import org.library.exception.LibraryException;
import org.library.service.MetaDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class AdminController {

    private MetaDataService metaDataService;

    public AdminController(MetaDataService metaDataService) {
        this.metaDataService = metaDataService;
    }

    @GetMapping("/metadata")
    public Collection<MetaData> getAllMetaData() throws LibraryException {
        return metaDataService.getAllMetadata();
    }
}
