package com.aposta.pagamentos.business.entity;

import com.aposta.pagamentos.business.type.TipoPessoa;

public class PagadorPessoaFisica extends Pagador {

    private String cpf;
    private Integer idade;

    public PagadorPessoaFisica(String cpf, Integer idade) {
        super(TipoPessoa.PESSOA_FISICA);
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public boolean ehMenorDeIdade() {
        return idade < 18;
    }

    @Override
    public boolean ehPessoaJuridica() {
        return false;
    }

    @Override
    public String getID() {
        return cpf;
    }
}
