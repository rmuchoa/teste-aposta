package com.aposta.pagamentos.business.pagador.entity;

public class PagadorPessoaFisicaBuilder {

    private String cpf;
    private Integer idade;

    private PagadorPessoaFisicaBuilder() {}

    public static PagadorPessoaFisicaBuilder builder() {
        return new PagadorPessoaFisicaBuilder();
    }

    public PagadorPessoaFisicaBuilder comCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public PagadorPessoaFisicaBuilder comAlgumCpf() {
        return comCpf("12345678900");
    }

    public PagadorPessoaFisicaBuilder comIdade(Integer idade) {
        this.idade = idade;
        return this;
    }

    public PagadorPessoaFisicaBuilder comDesesseteAnos() {
        return comIdade(17);
    }

    public PagadorPessoaFisicaBuilder comDesoitoAnos() {
        return comIdade(18);
    }

    public static Pagador buildPagadorMenorDeIdade() {
        return builder()
                .comAlgumCpf()
                .comDesesseteAnos()
                .build();
    }

    public static Pagador buildPagadorValido() {
        return builder()
                .comAlgumCpf()
                .comDesoitoAnos()
                .build();
    }

    public Pagador build() {
        return new PagadorPessoaFisica(cpf, idade);
    }
}
