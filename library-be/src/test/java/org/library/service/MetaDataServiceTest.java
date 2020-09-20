package org.library.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.library.dto.MetaData;
import org.library.exception.LibraryException;
import org.library.repository.MetaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.library.service.MetaDataService.CHECKOUT_DURATION;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
@TestPropertySource(locations="classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MetaDataServiceTest {

    @Autowired
    private MetaDataService service;
    @Autowired
    private MetaDataRepository repository;

    @Test
    public void persistCheckoutDurationTest() throws LibraryException {
        service.persistCheckoutDuration(90);

        MetaData actual = repository.findById(CHECKOUT_DURATION).get();
        assertEquals("90", actual.getMetaValue());
    }

    @Test(expected = LibraryException.class)
    public void persistCheckoutDurationInvalidTest() throws LibraryException {
        service.persistCheckoutDuration(-1);
    }

    @Test
    public void getCheckoutDurationTest() throws LibraryException {
        repository.save(MetaData.builder().metaKey(CHECKOUT_DURATION).metaValue("45").build());

        int actual = service.getCheckoutDuration();
        assertEquals(45, actual);
    }

    @Test(expected = LibraryException.class)
    public void getCheckoutDurationInvalidTest() throws LibraryException {
        service.getCheckoutDuration();
    }
}
