package org.library.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.library.repository.dto.User;
import org.library.repository.dto.UserTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class UserTransactionRepositoryTest {
    @Autowired
    private UserTransactionRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private User user = User.builder().userName("Richard Green").userType("Customer").password("xyz$$").build();

    @Before
    public void setUp() {
        user = entityManager.persist(user);
    }

    @Test
    public void testSave() {
        UserTransaction expected = UserTransaction.builder().
                tranDate(new Date()).
                user(user).
                checkOutQty(10).
                dueDate(new Date()).
                status("OPEN").
                build();

        expected = repository.save(expected);
        log.info(expected.toString());

        assertNotNull(expected.getId());

        UserTransaction actual = entityManager.find(UserTransaction.class, expected.getId());
        assertEquals(expected, actual);
    }
}
