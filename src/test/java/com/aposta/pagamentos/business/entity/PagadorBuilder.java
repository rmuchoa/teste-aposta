package com.aposta.pagamentos.business.entity;

import com.aposta.pagamentos.business.type.TipoPessoa;

public class PagadorBuilder {

    private Integer idade;
    private TipoPessoa tipo;

    private PagadorBuilder() {}

    public static PagadorBuilder builder() {
        return new PagadorBuilder();
    }

    public PagadorBuilder comIdade(Integer idade) {
        this.idade = idade;
        return this;
    }

    public PagadorBuilder comDesesseteAnos() {
        return comIdade(17);
    }

    public PagadorBuilder comDesoitoAnos() {
        return comIdade(18);
    }

    public PagadorBuilder comTipo(TipoPessoa tipo) {
        this.tipo = tipo;
        return this;
    }

    public PagadorBuilder comTipoPessoaJuridica() {
        return comTipo(TipoPessoa.PESSOA_JURIDICA);
    }

    public PagadorBuilder comTipoPessoaFisica() {
        return comTipo(TipoPessoa.PESSOA_FISICA);
    }

    public Pagador build() {
        return new Pagador(idade, tipo);
    }
}
