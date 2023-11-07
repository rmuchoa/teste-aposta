package com.aposta.pagamentos.business.entity;

import com.aposta.pagamentos.business.type.TipoPessoa;

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
    public String getID() {
        return cnpj;
    }

}
