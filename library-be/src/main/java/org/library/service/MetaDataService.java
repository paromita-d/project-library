package org.library.service;

import lombok.extern.slf4j.Slf4j;
import org.library.dto.MetaData;
import org.library.exception.LibraryException;
import org.library.repository.MetaDataRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class MetaDataService {

    public static final String CHECKOUT_DURATION = "checkout_duration";

    private MetaDataRepository repository;

    public MetaDataService(MetaDataRepository repository) {
        this.repository = repository;
    }

    public void persistCheckoutDuration(int duration) throws LibraryException {
        if(duration <= 0) {
            throw new LibraryException("Duration should be positive. Found " + duration);
        }
        try {
            MetaData metaData = repository.save(MetaData.builder().metaKey(CHECKOUT_DURATION).metaValue(String.valueOf(duration)).build());
            log.info("persisted " + metaData);
        } catch (Exception e) {
            throw new LibraryException("Exception inserting checkout duration", e);
        }
    }

    public int getCheckoutDuration() throws LibraryException {
        try {
            Optional<MetaData> optional = repository.findById(CHECKOUT_DURATION);
            if(optional.isPresent())
                return Integer.parseInt(optional.get().getMetaValue());
        } catch (Exception e) {
            throw new LibraryException("Exception fetching checkout duration", e);
        }
        throw new LibraryException(CHECKOUT_DURATION + " not found in DB");
    }

    public Collection<MetaData> getAllMetadata() throws LibraryException {
        try {
            List<MetaData> list = new ArrayList<>();
            repository.findAll().forEach(list::add);
            return list;
        } catch (Exception e) {
            throw new LibraryException("Exception fetching metadata", e);
        }
    }
}
