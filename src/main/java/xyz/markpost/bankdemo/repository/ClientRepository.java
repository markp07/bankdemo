package xyz.markpost.bankdemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import xyz.markpost.bankdemo.model.Client;

@Component
public interface ClientRepository extends CrudRepository<Client, Long> {

}
