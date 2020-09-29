package org.library.service;

import lombok.extern.slf4j.Slf4j;
import org.library.repository.dto.MetaData;
import org.library.exception.LibraryException;
import org.library.repository.MetaDataRepository;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.http.HttpStatus.*;

@Service
@Slf4j
public class MetaDataService {

    public static final String CHECKOUT_DURATION = "checkout_duration";
    // below line is equivalent to slf4j lombok annotation
    //private static final Logger log = org.slf4j.LoggerFactory.getLogger(MetaDataService.class);

    private final MetaDataRepository repository;
    // The constructor is equivalent to @Autowired. As we mention the service from the controller class, we mention the repository from service class
    public MetaDataService(MetaDataRepository repository) {
        this.repository = repository;
    }

    // to handle the exception
    public int getCheckoutDuration() throws LibraryException {
        try {
            Optional<MetaData> optional = repository.findById(CHECKOUT_DURATION);
            if(optional.isPresent())
                return Integer.parseInt(optional.get().getMetaValue());
        } catch (Exception e) {
            throw new LibraryException(e);
        }
        throw new LibraryException(CHECKOUT_DURATION + " not found in DB");
    }

    // to save in the DB - map to list
    public void persistMetadata(Map<String, String> metaMap) throws LibraryException {
        if(metaMap.containsKey(CHECKOUT_DURATION) && Integer.parseInt(metaMap.get(CHECKOUT_DURATION)) <= 0) {
            throw new LibraryException("Duration should be positive. Found " + metaMap.get(CHECKOUT_DURATION), REQUESTED_RANGE_NOT_SATISFIABLE);
        }

        try {
            List<MetaData> metaDataList = new ArrayList<>();
            metaMap.forEach((k, v) -> metaDataList.add(MetaData.builder().metaKey(k).metaValue(v).build()));

            repository.saveAll(metaDataList);
            log.info("persisted " + metaDataList);
        } catch (Exception e) {
            throw new LibraryException(e, INTERNAL_SERVER_ERROR);
        }
    }

    // to retrieve from DB - list to map
    public Map<String, String> getAllMetadata() throws LibraryException {
        try {
            Map<String, String> metaMap = new LinkedHashMap<>();
            repository.findAll().forEach(e -> metaMap.put(e.getMetaKey(), e.getMetaValue()));
            log.info("fetched all metadata {}", metaMap);
            return metaMap;
        } catch (Exception e) {
            throw new LibraryException(e);
        }
    }
}
