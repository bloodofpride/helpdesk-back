package com.maxwell.helpdesk.controllers;

import com.maxwell.helpdesk.domain.dtos.TecnicoDTO;
import com.maxwell.helpdesk.services.TecnicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoController {
    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok(tecnicoService.findById(id));
    }

    @GetMapping()
    public ResponseEntity<List<TecnicoDTO>> findAll(){
        return ResponseEntity.ok(tecnicoService.findAll());
    }

    @PostMapping
    public ResponseEntity<TecnicoDTO> save(@RequestBody TecnicoDTO objDTO){
        TecnicoDTO newDTO = tecnicoService.save(objDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(newDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
