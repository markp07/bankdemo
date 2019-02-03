package xyz.markpost.bankdemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import xyz.markpost.bankdemo.model.Transaction;

@Component
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
