package org.library.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.library.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Autowired
    private TestEntityManager entityManager; // to test the repository

    @Test
    public void testSave() {
        User expected = User.builder().userName("Paromita D").userType("Customer").password("abc123").build();
        expected = repository.save(expected);
        log.info(expected.toString());

        assertNotNull(expected.getId());

        User actual = entityManager.find(User.class, expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate() {
        User user = User.builder().userName("Paromita D").userType("Customer").password("abc123").build();
        user = entityManager.persist(user);
        assertNotNull(user.getId());

        user.setPassword("123abc");
        user = repository.save(user);
        log.info(user.toString());
        assertNotNull(user.getId());

        User actual = entityManager.find(User.class, user.getId());
        assertEquals("123abc", actual.getPassword());
    }

    @Test
    public void testGet() {
        User actual = User.builder().userName("Richard Green").userType("Customer").password("xyz$$").build();
        actual = entityManager.persist(actual);
        assertNotNull(actual.getId());

        User expected = repository.findById(actual.getId()).get();
        log.info(expected.toString());
        assertEquals(actual, expected);
    }
}


