package xyz.markpost.bankdemo.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.markpost.bankdemo.repository.AccountRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AccountControllerUT {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private AccountRepository accountRepository;

  @Test
  public void test() {

  }

}