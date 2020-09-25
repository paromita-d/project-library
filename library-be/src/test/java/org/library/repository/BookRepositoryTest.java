package org.library.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.library.repository.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class BookRepositoryTest {
    @Autowired
    private BookRepository repository;

    @Autowired
    private TestEntityManager entityManager; // to test the repository

    @Test
    public void testSave() {
        Book expected = Book.builder().bookName("First Book of maths").author("Carol B.").description("Its her first published book").quantity(10).availability(7).build();
        expected = repository.save(expected);
        log.info(expected.toString());

        assertNotNull(expected.getId());

        Book actual = entityManager.find(Book.class, expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate() {
        Book book = Book.builder().bookName("My First Book").author("Carol B.").description("Its her first published book").quantity(10).availability(7).build();
        book = entityManager.persist(book);
        assertNotNull(book.getId());

        book.setDescription("This book is awesome");
        book = repository.save(book);
        log.info(book.toString());
        assertNotNull(book.getId());

        Book actual = entityManager.find(Book.class, book.getId());
        assertEquals("This book is awesome", actual.getDescription());
    }

    @Test
    public void testGet() {
        Book actual = Book.builder().bookName("Alien Study").author("Elizabeth Reid").description("book on outer space").quantity(12).availability(5).build();
        actual = entityManager.persist(actual);
        assertNotNull(actual.getId());

        Book expected = repository.findById(actual.getId()).get();
        log.info(expected.toString());
        assertEquals(actual, expected);
    }
}

