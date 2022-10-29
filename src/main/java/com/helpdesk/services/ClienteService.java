package com.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpdesk.entity.dtos.ClienteDTO;
import com.helpdesk.entity.model.Pessoa;
import com.helpdesk.entity.model.Cliente;
import com.helpdesk.repository.PessoaRepository;
import com.helpdesk.repository.ClienteRepository;
import com.helpdesk.services.exception.DataIntegrityViolationException;
import com.helpdesk.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository tecnicoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Cliente findById(Integer id) {
        Optional<Cliente> opt = tecnicoRepository.findById(id);
        return opt.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    public List<Cliente> findAll() {
        return tecnicoRepository.findAll();
    }

    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null);
        validaPorCpfEEmail(objDTO);
        Cliente newObj = new Cliente(objDTO);
        return tecnicoRepository.save(newObj);
    }

    private void validaPorCpfEEmail(ClienteDTO objDTO) {
        Optional<Pessoa> objOpt = pessoaRepository.findByCpf(objDTO.getCpf());
        if (objOpt.isPresent() && objOpt.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("CPF informado já cadastrado na base de dados!");
        }

        objOpt = pessoaRepository.findByEmail(objDTO.getEmail());
        if (objOpt.isPresent() && objOpt.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("E-mail informado já cadastrado na base de dados!");
        }
    }

    public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
        objDTO.setId(id);
        Cliente oldObj = findById(id);
        validaPorCpfEEmail(objDTO);
        oldObj = new Cliente(objDTO);
        return tecnicoRepository.save(oldObj);
    }

    public void delete(Integer id) {
        Cliente obj = findById(id);
        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException(
                    "Cliente possui ordens de serviço e por isso não pode ser deletado!");
        }
        tecnicoRepository.deleteById(id);
    }
}
