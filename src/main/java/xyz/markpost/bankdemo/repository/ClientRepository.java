package xyz.markpost.bankdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import xyz.markpost.bankdemo.model.Client;

@Component
public interface ClientRepository extends JpaRepository<Client, Long> {

}
