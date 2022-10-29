package com.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpdesk.entity.dtos.TecnicoDTO;
import com.helpdesk.entity.model.Pessoa;
import com.helpdesk.entity.model.Tecnico;
import com.helpdesk.repository.PessoaRepository;
import com.helpdesk.repository.TecnicoRepository;
import com.helpdesk.services.exception.DataIntegrityViolationException;
import com.helpdesk.services.exception.ObjectNotFoundException;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Tecnico findById(Integer id) {
        Optional<Tecnico> opt = tecnicoRepository.findById(id);
        return opt.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    public List<Tecnico> findAll() {
        return tecnicoRepository.findAll();
    }

    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null);
        validaPorCpfEEmail(objDTO);
        Tecnico newObj = new Tecnico(objDTO);
        return tecnicoRepository.save(newObj);
    }

    private void validaPorCpfEEmail(TecnicoDTO objDTO) {
        Optional<Pessoa> objOpt = pessoaRepository.findByCpf(objDTO.getCpf());
        if (objOpt.isPresent() && objOpt.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("CPF informado já cadastrado na base de dados!");
        }

        objOpt = pessoaRepository.findByEmail(objDTO.getEmail());
        if (objOpt.isPresent() && objOpt.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("E-mail informado já cadastrado na base de dados!");
        }
    }

    public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
        objDTO.setId(id);
        Tecnico oldObj = findById(id);
        validaPorCpfEEmail(objDTO);
        oldObj = new Tecnico(objDTO);
        return tecnicoRepository.save(oldObj);
    }
}
