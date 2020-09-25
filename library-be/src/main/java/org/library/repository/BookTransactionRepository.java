package org.library.repository;

import org.library.dto.BookTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTransactionRepository extends CrudRepository<BookTransaction, Long>  {
}
