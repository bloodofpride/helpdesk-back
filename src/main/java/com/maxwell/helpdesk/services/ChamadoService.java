package com.maxwell.helpdesk.services;

import com.maxwell.helpdesk.domain.Chamado;
import com.maxwell.helpdesk.domain.Cliente;
import com.maxwell.helpdesk.domain.Tecnico;
import com.maxwell.helpdesk.domain.dtos.ChamadaDTO;
import com.maxwell.helpdesk.domain.enums.Prioridade;
import com.maxwell.helpdesk.domain.enums.Status;
import com.maxwell.helpdesk.repositories.ChamadoRepository;
import com.maxwell.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChamadoService {
    private final ChamadoRepository chamadoRepository;
    private final TecnicoService tecnicoService;
    private final ClienteService clienteService;

    public ChamadoService(ChamadoRepository chamadoRepository, TecnicoService tecnicoService, ClienteService clienteService) {
        this.chamadoRepository = chamadoRepository;
        this.tecnicoService = tecnicoService;
        this.clienteService = clienteService;
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

    public ChamadaDTO save(ChamadaDTO objDTO) {
        return new ChamadaDTO(chamadoRepository.save(newChamado(objDTO)));
    }

    private Chamado newChamado(ChamadaDTO obj){
        Cliente cliente = clienteService.findById(obj.getCliente());
        Tecnico tecnico = tecnicoService.findById(obj.getTecnico());

        Chamado chamado = new Chamado();
        if(obj.getId() != null){
            chamado.setId(obj.getId());
        }

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
        chamado.setStatus(Status.toEnum(obj.getStatus()));
        chamado.setDataAbertura(obj.getDataAbertura());
        chamado.setDataFechamento(obj.getDataFechamento());
        chamado.setTitulo(obj.getTitulo());
        chamado.setObservacoes(obj.getObservacoes());
        return chamado;
    }
}
