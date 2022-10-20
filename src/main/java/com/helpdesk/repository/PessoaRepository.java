package com.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helpdesk.entity.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

}
