package org.library.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.library.repository.dto.Book;
import org.library.repository.dto.BookTransaction;
import org.library.repository.dto.User;
import org.library.repository.dto.UserTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class BookTransactionRepositoryTest {
    @Autowired
    private BookTransactionRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private Book book;
    private User user;
    private UserTransaction userTransaction;

    @Before
    public void setup() {
        book = Book.builder().
                bookName("First Book of maths").
                author("Carol B.").
                description("Its her first published book").
                quantity(10).
                availability(7).
                build();

        user = User.builder().
                userName("Richard Green").
                userType("Customer").
                password("xyz$$").
                build();

        userTransaction = UserTransaction.builder().
                tranDate(new Date()).
                user(user).
                checkOutQty(10).
                dueDate(new Date()).
                status("OPEN").
                build();

        book = entityManager.persist(book);
        user = entityManager.persist(user);
        userTransaction = entityManager.persist(userTransaction);
    }

    @Test
    public void testSave() {
        BookTransaction expected = BookTransaction.builder().
                userTransaction(userTransaction).
                book(book).
                returned(false).
                overdue(true).
                build();

        expected = repository.save(expected);
        log.info(expected.toString());

        assertNotNull(expected.getId());

        BookTransaction actual = entityManager.find(BookTransaction.class, expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByOverdue() {
        BookTransaction tran1 = BookTransaction.builder().
                userTransaction(userTransaction).
                book(book).
                returned(false).
                overdue(true).
                build();

        BookTransaction tran2 = BookTransaction.builder().
                userTransaction(userTransaction).
                book(book).
                returned(false).
                overdue(false).
                build();

        repository.saveAll(Arrays.asList(tran1, tran2));

        List<BookTransaction> trans = repository.findByOverdue(true);
        assertEquals(tran1, trans.get(0));

        trans = repository.findByOverdue(false);
        assertEquals(tran2, trans.get(0));
    }
}
