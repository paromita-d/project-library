package org.library.service;

import lombok.extern.slf4j.Slf4j;
import org.library.controller.dto.BookDTO;
import org.library.controller.dto.UserDTO;
import org.library.repository.BookRepository;
import org.library.repository.BookTransactionRepository;
import org.library.repository.dto.Book;
import org.library.repository.dto.BookTransaction;
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
    private final BookRepository bookRepository;
    private final BookTransactionRepository bookTransactionRepository;

    // The constructor is equivalent to @Autowired. As we mention the service from the controller class, we mention the repository from service class
    public AdminService(MetaDataRepository metaDataRepository,
                        BookRepository bookRepository,
                        BookTransactionRepository bookTransactionRepository) {
        this.metaDataRepository = metaDataRepository;
        this.bookRepository = bookRepository;
        this.bookTransactionRepository = bookTransactionRepository;
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

    // to save in DB
    public Long addBookToRepo(BookDTO bookDTO) throws LibraryException {
        if(bookDTO.getAvailability() == null || bookDTO.getQuantity() == null || bookDTO.getAvailability() > bookDTO.getQuantity()) {
            throw new LibraryException("Availability " + bookDTO.getAvailability() + " should not be greater than total quantity " + bookDTO.getQuantity());
        }

        Book book = Book.builder().
                bookName(bookDTO.getBookName()).author(bookDTO.getAuthor()).
                description(bookDTO.getDescription()).quantity(bookDTO.getQuantity()).
                availability(bookDTO.getAvailability()).build();

        bookRepository.save(book);
        log.info("persisted " + book);
        return book.getId();
    }

    //update books in DB
    public Map<Long, Integer> updateBooksQtyInRepo(List<BookDTO> booksDTO) throws LibraryException {
        Map<Long, BookDTO> idBookMap = new HashMap<>(); //map of id and DTO
        booksDTO.forEach(dto -> {
            if(dto.getQuantity() >= 0)  //only update if quantity is non-negative
                idBookMap.put(dto.getId(), dto);
        });
        Iterable<Book> entities = bookRepository.findAllById(idBookMap.keySet()); //get entities corresponding to IDs
        if(!entities.iterator().hasNext())
            throw new LibraryException("None of these book ids exist in DB: " + idBookMap.keySet());

        Map<Long, Integer> idQtyMap = new HashMap<>();
        entities.forEach(entity -> {
            if(idBookMap.containsKey(entity.getId())) {     //update entity with dto quantity
                entity.setQuantity(idBookMap.get(entity.getId()).getQuantity());
                idQtyMap.put(entity.getId(), entity.getQuantity());
            }
        });
        bookRepository.saveAll(entities);  //persist back to DB
        return idQtyMap;
    }

    //get list of overdue users
    public Set<UserDTO> getOverDueUsers() {
        List<BookTransaction> overDueTrans = bookTransactionRepository.findByOverdue(true);

        Set<UserDTO> overdueUsers = new HashSet<>();
        overDueTrans.forEach(t -> overdueUsers.add(
                UserDTO.builder().
                        id(t.getUserTransaction().getUser().getId()).
                        userName(t.getUserTransaction().getUser().getUserName()).
                        build()));

        return overdueUsers;
    }
}
