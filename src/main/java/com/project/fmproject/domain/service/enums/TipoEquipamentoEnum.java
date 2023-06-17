package com.project.fmproject.domain.service.enums;

public enum TipoEquipamentoEnum {

    VASO(1, "Vaso de pressão"),
    VALVULA(2, "Válvula de segurança"),
    INDICADOR(3, "Indicador de pressão"),
    CALDEIRA(4, "Caldeira"),
    LINHA(5, "Linha de vida"),
    ELEVADOR(6, "Elevador"),
    EQUIPAMENTO(7, "Equipamento");

     TipoEquipamentoEnum(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    private final int codigo;
    private final String descricao;

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
