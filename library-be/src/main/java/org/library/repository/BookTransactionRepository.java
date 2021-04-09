package org.library.repository;

import org.library.repository.dto.BookTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookTransactionRepository extends CrudRepository<BookTransaction, Long>  {

    List<BookTransaction> findByOverdue(@Param("overdue") Boolean overdue);
}
