package org.library.repository;

import org.library.dto.UserTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTransactionRepository extends CrudRepository<UserTransaction, Long> {
}
