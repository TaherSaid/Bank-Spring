package com.example.web;


import java.util.Date;
import java.util.List;
import java.util.Optional;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dao.CompteRepository;
import com.example.dao.OperationRepository;
import com.example.entities.Compte;
import com.example.entities.Operation;



@Component
@Path("/Banque")
public class CompteResteService {
	@Autowired
	CompteRepository compteRep;
	@Autowired
	OperationRepository opRep;
	
	@GET
	@Path("/compte")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Compte> getcompte(){
		return compteRep.findAll();
	}
	
	@POST
	@Path("/compte")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Compte saveCompte(@RequestParam Compte cp) {
		return compteRep.save(cp);
		
	}
	@GET
	@Path("/compte/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Optional<Compte> getcompte(@PathParam(value="id") Long id ){
		return compteRep.findById(id);
	}
	@GET
	@Path("/compte/conversion/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public int conversion(@PathParam(value= "id") int id ){
		return id*3;
	}
	@DELETE
	@Path("/compte/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void delete(@PathParam(value= "id") Long id ){
		compteRep.deleteById(id);
	}
	@PUT
	@Path("/compte/Update/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Compte Update(@RequestParam Compte cp,@PathParam(value= "id") Long id ){
		 return compteRep.findById(id).map(comptes->{
													comptes.setSolde(cp.getSolde());
													comptes.setDateCreation(cp.getDateCreation());
													return compteRep.save(comptes);
													}).orElseGet(() -> {
												        return compteRep.save(cp);
												      });
	}
	
	@GET
	@Path("/verser")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Operation> getOperations(){
		return opRep.findAll();
	}
	
	@PUT
	@Path("/verser/{code}")
	@Produces (MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public Response verser(@PathParam (value="code") Long code,@RequestBody	int montant){
		Optional<Compte> cp = compteRep.findById(code);
		Compte cpt = cp.get();
		cpt.setSolde(cpt.getSolde()+(montant));
		compteRep.save(cpt);
		Operation op = new Operation();
		op.setMontant(montant);
		op.setDateOperation(new Date());
		op.setCompte(cpt);
		opRep.save(op) ;
		return Response
		.status(Response.Status.OK)
		.entity(cpt)
		.build();
	}
	
	@PUT
	@Path("/retrait/{code}")
	@Produces (MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public Response retrait(@PathParam (value="code") Long code,@RequestBody int montant){
	Optional<Compte> cp = compteRep.findById(code);
	Compte cpt = cp.get();
	cpt.setSolde(cpt.getSolde()-(montant));
	compteRep.save(cpt);
	Operation op = new Operation();
	op.setMontant(montant);
	op.setDateOperation(new Date());
	op.setCompte(cpt);
	opRep.save(op) ;
	return Response
	.status(Response.Status.OK)
	.entity(cpt)
	.build();
	}
	
	@GET
	@Path("/solde/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	public double getSoldebyId(@PathParam(value = "code") Long id){
		Compte p =  compteRep.findById(id).orElseThrow(() -> new RuntimeException("merci de verifier la disponibilité du compte"));
		return p.getSolde();
 	}
	
	@PUT
	@Path("/virement/{cp1}/{cp2}")
	@Produces (MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public void virement(@PathParam (value="cp1") Long id1,@PathParam (value="cp2") Long id2,@RequestBody long montant){
		retrait(id1,(int) montant);
		verser(id2,(int) montant);
		
		
	}
}
