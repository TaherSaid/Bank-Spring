package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Operation;

public interface OperationRepository extends JpaRepository<Operation, Long>{

}
