package com.maxwell.helpdesk.services;

import com.maxwell.helpdesk.domain.Chamado;
import com.maxwell.helpdesk.domain.Cliente;
import com.maxwell.helpdesk.domain.Tecnico;
import com.maxwell.helpdesk.domain.enums.Perfil;
import com.maxwell.helpdesk.domain.enums.Prioridade;
import com.maxwell.helpdesk.domain.enums.Status;
import com.maxwell.helpdesk.repositories.ChamadoRepository;
import com.maxwell.helpdesk.repositories.ClienteRepository;
import com.maxwell.helpdesk.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DBService {
    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ChamadoRepository chamadoRepository;

    public void instanciaDB(){
        Tecnico tec1 = new Tecnico(null, "Valdir Cezar", "63653230268", "valdir@mail.com", "123");
        tec1.addPerfil(Perfil.ADMIN);

        Cliente cli1 = new Cliente(null, "Linus Torvalds", "80527954780", "Torvalds@mail.com", "123");

        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "chamado 01", "Primeiro chamado", tec1, cli1);

        tecnicoRepository.saveAll(Arrays.asList(tec1));
        clienteRepository.saveAll(Arrays.asList(cli1));
        chamadoRepository.saveAll(Arrays.asList(c1));
    }
}
