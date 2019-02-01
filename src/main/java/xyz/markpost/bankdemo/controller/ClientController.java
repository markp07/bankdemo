package xyz.markpost.bankdemo.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.markpost.bankdemo.model.Client;
import xyz.markpost.bankdemo.service.ClientService;


@Path("client")
@Component
public class ClientController {
	
	@Autowired
	private ClientService clientService;

    @GET
    @Path("{id}/{transfer}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listClients(@PathParam("id") long id, 
			@PathParam("transfer") float transfer){
    	Client client = clientService.findById(id).get();
    	float balance = client.getBalance();
    	
    	if (transfer > 0) {
	    	balance = balance - transfer;
	    	client.setBalance(balance);
	    	clientService.save(client);
    	}

		Iterable <Client> clients = clientService.findAll();
		return Response.ok(clients).build();
	}
	
}
