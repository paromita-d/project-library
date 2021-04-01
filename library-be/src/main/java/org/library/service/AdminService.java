package org.library.service;

import lombok.extern.slf4j.Slf4j;
import org.library.repository.dto.MetaData;
import org.library.exception.LibraryException;
import org.library.repository.MetaDataRepository;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.library.controller.dto.MetaDataEnum.CHECKOUT_DURATION;
import static org.springframework.http.HttpStatus.*;

@Service
@Slf4j
public class AdminService {

    // below line is equivalent to slf4j lombok annotation
    //private static final Logger log = org.slf4j.LoggerFactory.getLogger(MetaDataService.class);

    private final MetaDataRepository metaDataRepository;
    // The constructor is equivalent to @Autowired. As we mention the service from the controller class, we mention the repository from service class
    public AdminService(MetaDataRepository metaDataRepository) {
        this.metaDataRepository = metaDataRepository;
    }

    // to handle the exception
    public int getCheckoutDuration() throws LibraryException {
        Optional<MetaData> optional = metaDataRepository.findById(CHECKOUT_DURATION.getMetaKey());
        if(optional.isPresent())
            return Integer.parseInt(optional.get().getMetaValue());

        throw new LibraryException(CHECKOUT_DURATION + " not found in DB");
    }

    // to save in the DB - map to list
    public void persistMetadata(Map<String, String> metaMap) throws LibraryException {
        String duration = metaMap.get(CHECKOUT_DURATION.getMetaKey());
        if(duration != null && Integer.parseInt(duration) <= 0) {
            throw new LibraryException("Duration should be positive. Found " + duration, REQUESTED_RANGE_NOT_SATISFIABLE);
        }

        List<MetaData> metaDataList = new ArrayList<>();
        metaMap.forEach((k, v) -> metaDataList.add(MetaData.builder().metaKey(k).metaValue(v).build()));

        metaDataRepository.saveAll(metaDataList);
        log.info("persisted " + metaDataList);
    }

    // to retrieve from DB - list to map
    public Map<String, String> getAllMetadata() {
        Map<String, String> metaMap = new LinkedHashMap<>();
        metaDataRepository.findAll().forEach(e -> metaMap.put(e.getMetaKey(), e.getMetaValue()));
        log.info("fetched all metadata {}", metaMap);
        return metaMap;
    }
}
