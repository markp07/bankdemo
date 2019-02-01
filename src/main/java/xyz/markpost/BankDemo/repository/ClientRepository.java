package xyz.markpost.BankDemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import xyz.markpost.BankDemo.model.Client;

@Component
public interface ClientRepository extends CrudRepository <Client, Long> {

}
