package org.library.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.library.dto.MetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MetaDataRepositoryTest {
    @Autowired
    private MetaDataRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSave() {
        MetaData expected = MetaData.builder().metaKey("key-1").metaValue("value-1").build();
        repository.save(expected);

        MetaData actual = entityManager.find(MetaData.class, "key-1");
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate() {
        MetaData metadata = MetaData.builder().metaKey("key-1").metaValue("value-1").build();
        entityManager.persist(metadata);

        metadata.setMetaValue("value-x");
        repository.save(metadata);

        MetaData actual = entityManager.find(MetaData.class, "key-1");
        assertEquals("value-x", actual.getMetaValue());
    }

    @Test
    public void testGet() {
        MetaData actual = MetaData.builder().metaKey("key-2").metaValue("value-2").build();
        entityManager.persist(actual);

        MetaData expected = repository.findById("key-2").get();
        assertEquals(actual, expected);

//        repository.findAll().forEach(System.out::println);
    }
}
