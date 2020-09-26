package org.library.repository;

import org.library.repository.dto.MetaData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaDataRepository extends CrudRepository<MetaData, String> {

}
