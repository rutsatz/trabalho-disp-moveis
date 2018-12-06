package com.unisc.trabalhodispmoveis.model;

import android.util.Log;

import com.unisc.trabalhodispmoveis.util.AppConstants;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pessoa implements Serializable {

    public static final long serialVersionUID = 1L;

    private int userId;
    private String nome;
    private String email;
    private Date dataNascimento;
    private String telefone;
    private String endereco;
    private String CPF;
    private String tipoServico;

    public Pessoa() {
    }

    public Pessoa(int userId, String nome, String email, Date dataNascimento, String telefone,
                  String endereco, String CPF, String tipoServico) {
        this.userId = userId;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.endereco = endereco;
        this.CPF = CPF;
        this.tipoServico = tipoServico;
    }

    public Pessoa(int userId, String nome, String email, Date dataNascimento, String telefone,
                  String endereco, String CPF) {
        this.userId = userId;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.endereco = endereco;
        this.CPF = CPF;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.DATE_FORMAT);
        Date d = null;
        try {
            d = sdf.parse(dataNascimento);
        } catch (ParseException e) {
            Log.e("teste", "Erro ao setar data de nascimento: " + dataNascimento);
        }
        this.dataNascimento = d;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDataNascimentoAsString() {
        if (dataNascimento != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.DATE_FORMAT);
            return sdf.format(dataNascimento);
        } else {
            return "";
        }
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

}
