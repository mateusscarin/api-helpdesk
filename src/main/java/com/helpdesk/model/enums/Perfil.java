package com.helpdesk.model.enums;

public enum Perfil {

    ADMIN(0, "ADMIN"),
    CLIENTE(1, "CLIENTE"),
    TECNICO(2, "TECNICO");

    private Integer codigo;
    private String descricao;

    private Perfil(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Status toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (Status status : Status.values()) {
            if (cod.equals(status.getCodigo())) {
                return status;
            }
        }

        throw new IllegalArgumentException("Perfil informado é inválido!");
    }
}
