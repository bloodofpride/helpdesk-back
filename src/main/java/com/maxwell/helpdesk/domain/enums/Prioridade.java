package com.maxwell.helpdesk.domain.enums;

public enum Prioridade {
    BAIXA(0, "BAIXA"),
    MEDIA(1, "MEDIA"),
    ALTA(2, "ALTA");

    private Integer codigo;
    private String descricao;

    Prioridade(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Prioridade toEnum(Integer cod){
        if(cod == null){
            return null;
        }

        for (Prioridade p: Prioridade.values()){
            if (cod.equals(p.getCodigo())){
                return p;
            }
        }

        throw new IllegalArgumentException("Prioridade Inválida");
    }
}
