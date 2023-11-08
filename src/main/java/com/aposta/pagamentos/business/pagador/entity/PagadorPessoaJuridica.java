package com.aposta.pagamentos.business.pagador.entity;

import com.aposta.pagamentos.business.pagador.type.TipoPessoa;

public class PagadorPessoaJuridica extends Pagador {

    private String cnpj;

    public PagadorPessoaJuridica(String cnpj) {
        super(TipoPessoa.PESSOA_JURIDICA);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public boolean ehPessoaJuridica() {
        return true;
    }

    @Override
    public boolean ehMenorDeIdade() {
        return false;
    }

    @Override
    public String getID() {
        return cnpj;
    }

}
