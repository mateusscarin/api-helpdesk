package com.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helpdesk.entity.model.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
