package com.maxwell.helpdesk.controllers;

import com.maxwell.helpdesk.domain.dtos.TecnicoDTO;
import com.maxwell.helpdesk.services.TecnicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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
        return ResponseEntity.ok(new TecnicoDTO(tecnicoService.findById(id)));
    }

    @GetMapping()
    public ResponseEntity<List<TecnicoDTO>> findAll(){
        return ResponseEntity.ok(tecnicoService.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TecnicoDTO> save(@Valid @RequestBody TecnicoDTO objDTO){
        TecnicoDTO newDTO = tecnicoService.save(objDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(newDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO){
        TecnicoDTO newDTO = tecnicoService.update(id, objDTO);
        return ResponseEntity.ok().body(newDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        tecnicoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
