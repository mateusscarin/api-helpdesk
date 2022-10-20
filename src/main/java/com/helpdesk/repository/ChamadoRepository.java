package com.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helpdesk.entity.model.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

}
