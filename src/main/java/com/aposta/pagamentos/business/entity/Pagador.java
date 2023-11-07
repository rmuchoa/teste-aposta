package com.aposta.pagamentos.business.entity;

import com.aposta.pagamentos.business.type.TipoPessoa;

public class Pagador {

    private Integer idade;
    private TipoPessoa tipo;

    public Pagador(Integer idade, TipoPessoa tipo) {
        this.idade = idade;
        this.tipo = tipo;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public TipoPessoa getTipo() {
        return tipo;
    }

    public void setTipo(TipoPessoa tipo) {
        this.tipo = tipo;
    }

    public boolean ehMenorDeIdade() {
        return idade < 18;
    }

    public boolean ehPessoaJuridica() {
        return TipoPessoa.PESSOA_JURIDICA.equals(tipo);
    }
}
