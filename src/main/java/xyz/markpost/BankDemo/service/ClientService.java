package xyz.markpost.BankDemo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.markpost.BankDemo.model.Client;
import xyz.markpost.BankDemo.repository.ClientRepository;


@Service
@Transactional
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	public Client save(Client trein){
		return clientRepository.save(trein);
	}
	
	public Optional<Client> findById(Long id) {
		return clientRepository.findById(id);
	}

	public Iterable <Client> findAll(){
		Iterable <Client> result = clientRepository.findAll();
		return result;
	}
	
}
