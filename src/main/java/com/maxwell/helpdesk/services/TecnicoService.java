package com.maxwell.helpdesk.services;

import com.maxwell.helpdesk.domain.Pessoa;
import com.maxwell.helpdesk.domain.Tecnico;
import com.maxwell.helpdesk.domain.dtos.TecnicoDTO;
import com.maxwell.helpdesk.repositories.PessoaRepository;
import com.maxwell.helpdesk.repositories.TecnicoRepository;
import com.maxwell.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.maxwell.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TecnicoService {
    private final TecnicoRepository tecnicoRepository;
    private final PessoaRepository pessoaRepository;
    private final BCryptPasswordEncoder encoder;

    public TecnicoService(TecnicoRepository tecnicoRepository, PessoaRepository pessoaRepository, BCryptPasswordEncoder encoder) {
        this.tecnicoRepository = tecnicoRepository;
        this.pessoaRepository = pessoaRepository;
        this.encoder = encoder;
    }

    public Tecnico findById(Integer id){
        return tecnicoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado com o id: "+id));
    }

    public List<TecnicoDTO> findAll() {
        return tecnicoRepository.findAll().stream()
                .map(TecnicoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public TecnicoDTO save(TecnicoDTO objDTO) {
        objDTO.setId(null);
        objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        validaPorCpfEEmail(objDTO);
        Tecnico tecnico = new Tecnico(objDTO);
        tecnicoRepository.save(tecnico);
        return new TecnicoDTO(tecnico);
    }

    @Transactional
    public TecnicoDTO update(Integer id, TecnicoDTO objDTO) {
        objDTO.setId(id);
        Tecnico oldObj = findById(id);

        if(!objDTO.getSenha().equals(oldObj.getSenha())){
            objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        }

        validaPorCpfEEmail(objDTO);
        oldObj = new Tecnico(objDTO);
        tecnicoRepository.save(oldObj);
        return new TecnicoDTO(oldObj);
    }

    public void delete(Integer id) {
        Tecnico obj = findById(id);
        if(obj.getChamados().size() > 0){
            throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado.");
        }
        tecnicoRepository.delete(obj);
    }

    private void validaPorCpfEEmail(TecnicoDTO objDTO) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if(obj.isPresent() && !Objects.equals(obj.get().getId(), objDTO.getId())){
            throw new DataIntegrityViolationException("Já existe um usuário com o cpf: "+objDTO.getCpf());
        }
        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if(obj.isPresent() && !Objects.equals(obj.get().getId(), objDTO.getId())){
            throw new DataIntegrityViolationException("Já existe um usuário com o email: "+objDTO.getEmail());
        }
    }
}
