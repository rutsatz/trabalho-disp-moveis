package com.unisc.trabalhodispmoveis.model;

import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable {

    public static final long serialVersionUID = 1L;

    private List<Pessoa> pessoas;

    private TipoPessoa tipoPessoa;

    /**
     * Dados do proprio usuario.
     */
    private Pessoa usuarioPessoa;

    public Usuario() {
    }

    public Usuario(List<Pessoa> pessoas, TipoPessoa tipoPessoa) {
        this.pessoas = pessoas;
        this.tipoPessoa = tipoPessoa;
    }

    public Usuario(TipoPessoa tipoPessoa, Pessoa usuarioPessoa) {
        this.pessoas = pessoas;
        this.tipoPessoa = tipoPessoa;
        this.usuarioPessoa = usuarioPessoa;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public Pessoa getUsuarioPessoa() {
        return usuarioPessoa;
    }

    public void setUsuarioPessoa(Pessoa usuarioPessoa) {
        this.usuarioPessoa = usuarioPessoa;
    }
}
