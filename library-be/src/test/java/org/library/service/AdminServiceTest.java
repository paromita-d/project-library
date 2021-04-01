package org.library.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.library.controller.dto.BookDTO;
import org.library.repository.BookRepository;
import org.library.repository.dto.Book;
import org.library.repository.dto.MetaData;
import org.library.exception.LibraryException;
import org.library.repository.MetaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.library.controller.dto.MetaDataEnum.CHECKOUT_DURATION;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
@TestPropertySource(locations="classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdminServiceTest {

    @Autowired
    private AdminService service;
    @Autowired
    private MetaDataRepository metaDataRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testPersistCheckoutDuration() throws LibraryException {
        Map<String, String> map = new HashMap<>();
        map.put(CHECKOUT_DURATION.getMetaKey(), "90");
        service.persistMetadata(map);

        MetaData actual = metaDataRepository.findById(CHECKOUT_DURATION.getMetaKey()).get();
        assertEquals("90", actual.getMetaValue());
    }

    @Test(expected = LibraryException.class)
    public void testPersistCheckoutDurationInvalid() throws LibraryException {
        Map<String, String> map = new HashMap<>();
        map.put(CHECKOUT_DURATION.getMetaKey(), "-1");
        service.persistMetadata(map);
    }

    @Test(expected = RuntimeException.class)
    public void testPersistCheckoutDurationNonInt() throws LibraryException {
        Map<String, String> map = new HashMap<>();
        map.put(CHECKOUT_DURATION.getMetaKey(), "abcd");
        service.persistMetadata(map);
    }

    @Test
    public void testGetCheckoutDuration() throws LibraryException {
        metaDataRepository.save(MetaData.builder().metaKey(CHECKOUT_DURATION.getMetaKey()).metaValue("45").build());

        int actual = service.getCheckoutDuration();
        assertEquals(45, actual);
    }

    @Test(expected = LibraryException.class)
    public void testGetCheckoutDurationInvalid() throws LibraryException {
        service.getCheckoutDuration();
    }

    @Test
    public void testGetAllMetadata() {
        List<MetaData> actual = new ArrayList<>();
        actual.add(MetaData.builder().metaKey("key-1").metaValue("val-1").build());
        actual.add(MetaData.builder().metaKey("key-2").metaValue("val-2").build());
        actual.add(MetaData.builder().metaKey("key-3").metaValue("val-3").build());
        metaDataRepository.saveAll(actual);

        Map<String, String> expected = service.getAllMetadata();
        assertEquals(expected.get("key-1"), actual.get(0).getMetaValue());
        assertEquals(expected.get("key-2"), actual.get(1).getMetaValue());
        assertEquals(expected.get("key-3"), actual.get(2).getMetaValue());
    }

    @Test
    public void testAddBookToRepo() throws LibraryException {
        BookDTO bookDTO = BookDTO.builder().bookName("Alice in Wonderland").author("Rudyard Kipling").
                description("Young Adults").quantity(120).availability(120).build();

        Long id = service.addBookToRepo(bookDTO);

        Book actual = bookRepository.findById(id).get();

        assertEquals(bookDTO.getBookName(), actual.getBookName());
        assertEquals(bookDTO.getAuthor(), actual.getAuthor());
        assertEquals(bookDTO.getDescription(), actual.getDescription());
        assertEquals(bookDTO.getQuantity(), actual.getQuantity());
        assertEquals(bookDTO.getAvailability(), actual.getAvailability());
    }

    @Test(expected = LibraryException.class)
    public void testAddBookToRepoInvalid() throws LibraryException {
        service.addBookToRepo(BookDTO.builder().quantity(20).availability(100).build());
    }

    @Test
    public void testUpdateBooksQtyInRepo() throws LibraryException {
        BookDTO book1 = BookDTO.builder().bookName("Alice in Wonderland").author("Rudyard Kipling").
                description("Young Adults").quantity(120).availability(120).build();
        BookDTO book2 = BookDTO.builder().bookName("Scandal in Bohemia").author("Sir Arthur Conan Doyle").
                description("Sherlock Holmes").quantity(50).availability(50).build();

        book1.setId(service.addBookToRepo(book1));
        book1.setQuantity(200);
        book2.setId(service.addBookToRepo(book2));
        book2.setQuantity(100);

        List<BookDTO> books = Arrays.asList(book1, book2);
        Map<Long, Integer> idQtyMap = service.updateBooksQtyInRepo(books);

        assertEquals(2, idQtyMap.size());
        assertEquals(idQtyMap.get(book1.getId()), book1.getQuantity());
        assertEquals(idQtyMap.get(book2.getId()), book2.getQuantity());
    }

    @Test
    public void testUpdateBooksQtyInRepoInvalidQty() throws LibraryException {
        BookDTO book1 = BookDTO.builder().bookName("Alice in Wonderland").author("Rudyard Kipling").
                description("Young Adults").quantity(120).availability(120).build();
        BookDTO book2 = BookDTO.builder().bookName("Scandal in Bohemia").author("Sir Arthur Conan Doyle").
                description("Sherlock Holmes").quantity(50).availability(50).build();

        book1.setId(service.addBookToRepo(book1));
        book1.setQuantity(-10);
        book2.setId(service.addBookToRepo(book2));
        book2.setQuantity(100);

        List<BookDTO> books = Arrays.asList(book1, book2);
        Map<Long, Integer> idQtyMap = service.updateBooksQtyInRepo(books);

        assertEquals(1, idQtyMap.size());
        assertFalse(idQtyMap.containsKey(book1.getId()));
        assertEquals(idQtyMap.get(book2.getId()), book2.getQuantity());
    }

    @Test(expected = LibraryException.class)
    public void testUpdateBooksQtyInRepoInvalidIds() throws LibraryException {
        BookDTO book1 = BookDTO.builder().bookName("Alice in Wonderland").author("Rudyard Kipling").
                description("Young Adults").quantity(120).availability(120).build();
        BookDTO book2 = BookDTO.builder().bookName("Scandal in Bohemia").author("Sir Arthur Conan Doyle").
                description("Sherlock Holmes").quantity(50).availability(50).build();

        book1.setId(service.addBookToRepo(book1) + 1);
        book1.setQuantity(-10);

        List<BookDTO> books = Arrays.asList(book1, book2);
        service.updateBooksQtyInRepo(books);
    }
}
