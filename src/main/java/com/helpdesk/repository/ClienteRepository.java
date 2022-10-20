package com.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helpdesk.entity.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
