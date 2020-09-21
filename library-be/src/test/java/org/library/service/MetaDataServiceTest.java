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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void testPersistCheckoutDuration() throws LibraryException {
        Map<String, String> map = new HashMap<>();
        map.put(CHECKOUT_DURATION, "90");
        service.persistMetadata(map);

        MetaData actual = repository.findById(CHECKOUT_DURATION).get();
        assertEquals("90", actual.getMetaValue());
    }

    @Test(expected = LibraryException.class)
    public void testPersistCheckoutDurationInvalid() throws LibraryException {
        Map<String, String> map = new HashMap<>();
        map.put(CHECKOUT_DURATION, "-1");
        service.persistMetadata(map);
    }

    @Test(expected = LibraryException.class)
    public void testPersistCheckoutDurationNonInt() throws LibraryException {
        Map<String, String> map = new HashMap<>();
        map.put(CHECKOUT_DURATION, "abcd");
        service.persistMetadata(map);
    }

    @Test
    public void testGetCheckoutDuration() throws LibraryException {
        repository.save(MetaData.builder().metaKey(CHECKOUT_DURATION).metaValue("45").build());

        int actual = service.getCheckoutDuration();
        assertEquals(45, actual);
    }

    @Test(expected = LibraryException.class)
    public void testGetCheckoutDurationInvalid() throws LibraryException {
        service.getCheckoutDuration();
    }

    @Test
    public void testGetAllMetadata() throws LibraryException {
        List<MetaData> actual = new ArrayList<>();
        actual.add(MetaData.builder().metaKey("key-1").metaValue("val-1").build());
        actual.add(MetaData.builder().metaKey("key-2").metaValue("val-2").build());
        actual.add(MetaData.builder().metaKey("key-3").metaValue("val-3").build());
        repository.saveAll(actual);

        Map<String, String> expected = service.getAllMetadata();
        assertEquals(expected.get("key-1"), actual.get(0).getMetaValue());
        assertEquals(expected.get("key-2"), actual.get(1).getMetaValue());
        assertEquals(expected.get("key-3"), actual.get(2).getMetaValue());
    }
}
