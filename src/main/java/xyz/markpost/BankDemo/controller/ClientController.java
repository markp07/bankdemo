package xyz.markpost.BankDemo.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.markpost.BankDemo.model.Client;
import xyz.markpost.BankDemo.service.ClientService;


@Path("client")
@Component
public class ClientController {
	
	@Autowired
	private ClientService clientService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listGroep(){
		Iterable <Client> treinen = clientService.findAll();
		return Response.ok(treinen).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response postTrein(Client client){
		Client result = clientService.save(client);
		return Response.accepted(result.getId()).build();	
	}
	
}
