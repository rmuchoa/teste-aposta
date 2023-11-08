package com.aposta.pagamentos.business.pagador.entity;

import com.aposta.pagamentos.business.pagador.type.TipoPessoa;

public abstract class Pagador {

    private TipoPessoa tipo;

    public Pagador(TipoPessoa tipo) {
        this.tipo = tipo;
    }

    public TipoPessoa getTipo() {
        return tipo;
    }

    public void setTipo(TipoPessoa tipo) {
        this.tipo = tipo;
    }

    public abstract boolean ehPessoaJuridica();

    public abstract boolean ehMenorDeIdade();

    public abstract String getID();

}
