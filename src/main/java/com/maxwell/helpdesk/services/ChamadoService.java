package com.maxwell.helpdesk.services;

import com.maxwell.helpdesk.domain.Chamado;
import com.maxwell.helpdesk.domain.dtos.ChamadaDTO;
import com.maxwell.helpdesk.repositories.ChamadoRepository;
import com.maxwell.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChamadoService {
    private final ChamadoRepository chamadoRepository;

    public ChamadoService(ChamadoRepository chamadoRepository) {
        this.chamadoRepository = chamadoRepository;
    }

    public Chamado findById(Integer id){
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado com o id: "+id));
    }

    public List<ChamadaDTO> findAll() {
        return chamadoRepository.findAll().stream()
                .map(ChamadaDTO::new)
                .collect(Collectors.toList());
    }
}
