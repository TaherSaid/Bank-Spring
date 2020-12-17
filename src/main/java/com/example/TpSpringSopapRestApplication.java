package com.example;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.dao.CompteRepository;
import com.example.dao.OperationRepository;
import com.example.entities.Compte;

@SpringBootApplication
public class TpSpringSopapRestApplication implements CommandLineRunner{
	
	@Autowired
	private CompteRepository compteRep;
	
	public static void main(String[] args) {
		SpringApplication.run(TpSpringSopapRestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Compte compte1=compteRep.save(new Compte(1,59872,new Date()));
		Compte compte3=compteRep.save(new Compte(2,1285,new Date()));
		Compte compte2=compteRep.save(new Compte(3,53030,new Date()));
	}

	
}
