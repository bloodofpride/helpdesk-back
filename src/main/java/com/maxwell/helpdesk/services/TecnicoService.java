package com.maxwell.helpdesk.services;

import com.maxwell.helpdesk.domain.Tecnico;
import com.maxwell.helpdesk.domain.dtos.TecnicoDTO;
import com.maxwell.helpdesk.repositories.TecnicoRepository;
import com.maxwell.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TecnicoService {
    private final TecnicoRepository tecnicoRepository;

    public TecnicoService(TecnicoRepository tecnicoRepository) {
        this.tecnicoRepository = tecnicoRepository;
    }

    public TecnicoDTO findById(Integer id){
        Tecnico tecnico = tecnicoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado com o id: "+id));
        return new TecnicoDTO(tecnico);
    }

    public List<TecnicoDTO> findAll() {
        return tecnicoRepository.findAll().stream()
                .map(TecnicoDTO::new)
                .collect(Collectors.toList());
    }
}
