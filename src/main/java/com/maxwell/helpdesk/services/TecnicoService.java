package com.maxwell.helpdesk.services;

import com.maxwell.helpdesk.domain.Tecnico;
import com.maxwell.helpdesk.domain.dtos.TecnicoDTO;
import com.maxwell.helpdesk.repositories.TecnicoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TecnicoService {
    private final TecnicoRepository tecnicoRepository;

    public TecnicoService(TecnicoRepository tecnicoRepository) {
        this.tecnicoRepository = tecnicoRepository;
    }

    public TecnicoDTO findById(Integer id){
        Optional<Tecnico> tecnico = tecnicoRepository.findById(id);
        return new TecnicoDTO(tecnico.get());
    }
}
