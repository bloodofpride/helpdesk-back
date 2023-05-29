package com.maxwell.helpdesk.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maxwell.helpdesk.domain.Cliente;
import com.maxwell.helpdesk.domain.enums.Perfil;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClienteDTO {
    private Integer id;
    @NotNull(message = "O campo NOME é obrigatório.")
    private String nome;
    @NotNull(message = "O campo CPF é obrigatório.")
    private String cpf;
    @NotNull(message = "O campo EMAIL é obrigatório.")
    private String email;
    @NotNull(message = "O campo SENHA é obrigatório.")
    private String senha;
    private Set<Integer> perfis = new HashSet<>();
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCriacao = LocalDate.now();

    public ClienteDTO(){

    }

    public ClienteDTO(Cliente cli) {
        this.id = cli.getId();
        this.nome = cli.getNome();
        this.cpf = cli.getCpf();
        this.email = cli.getEmail();
        this.senha = cli.getSenha();

        this.perfis.addAll(cli.getPerfis().stream()
                .map(Perfil::getCodigo)
                .collect(Collectors.toSet()));

        this.dataCriacao = cli.getDataCriacao();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
