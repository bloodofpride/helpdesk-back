package com.maxwell.helpdesk.controllers;

import com.maxwell.helpdesk.domain.dtos.ChamadaDTO;
import com.maxwell.helpdesk.services.ChamadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
