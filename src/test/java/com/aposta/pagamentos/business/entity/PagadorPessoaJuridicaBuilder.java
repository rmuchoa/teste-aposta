package com.aposta.pagamentos.business.entity;

public class PagadorPessoaJuridicaBuilder {

    private String cnpj;

    private PagadorPessoaJuridicaBuilder() {}

    public static PagadorPessoaJuridicaBuilder builder() {
        return new PagadorPessoaJuridicaBuilder();
    }

    public PagadorPessoaJuridicaBuilder comCnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public PagadorPessoaJuridicaBuilder comAlgumCnpj() {
        return comCnpj("12345678900");
    }

    public Pagador build() {
        return new PagadorPessoaJuridica(cnpj);
    }
}
