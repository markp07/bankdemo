package xyz.markpost.bankdemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import xyz.markpost.bankdemo.model.Account;

@Component
public interface AccountRepository extends CrudRepository<Account, Long> {

}
