package com.maxwell.helpdesk.controllers;

import com.maxwell.helpdesk.domain.dtos.ChamadaDTO;
import com.maxwell.helpdesk.services.ChamadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {
    private final ChamadoService chamadoService;

    public ChamadoController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChamadaDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(new ChamadaDTO(chamadoService.findById(id)));
    }

    @GetMapping()
    public ResponseEntity<List<ChamadaDTO>> findAll(){
        return ResponseEntity.ok().body(chamadoService.findAll());
    }

    @PostMapping
    public ResponseEntity<ChamadaDTO> create(@Valid @RequestBody ChamadaDTO objDTO){
        ChamadaDTO newDTO = chamadoService.save(objDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(newDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
