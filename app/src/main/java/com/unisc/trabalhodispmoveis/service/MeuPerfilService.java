package com.unisc.trabalhodispmoveis.service;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.unisc.trabalhodispmoveis.model.Pessoa;
import com.unisc.trabalhodispmoveis.model.Usuario;
import com.unisc.trabalhodispmoveis.util.AppConstants;
import com.unisc.trabalhodispmoveis.util.HttpUtils;

public class MeuPerfilService {


    public boolean salvarCliente(Usuario usuario, JsonHttpResponseHandler handler) {
        RequestParams rp = populaCampos(usuario.getUsuarioPessoa());
        HttpUtils.post(AppConstants.WS_CRIA_CLIENTE, rp, handler);
        return false;
    }

    public boolean salvarPrestador(Usuario usuario, JsonHttpResponseHandler handler) {
        RequestParams rp = populaCampos(usuario.getUsuarioPessoa());
        rp.add("tipo_servico", usuario.getUsuarioPessoa().getTipoServico());
        HttpUtils.post(AppConstants.WS_CRIA_PRESTADOR, rp, handler);
        return false;
    }

    private RequestParams populaCampos(Pessoa pessoa) {
        RequestParams rp = new RequestParams();
        rp.add("id_login", String.valueOf(pessoa.getUserId()));
        rp.add("nome", pessoa.getNome());
        rp.add("dt_nasc", pessoa.getDataNascimentoAsString());
        rp.add("telefone", pessoa.getTelefone());
        rp.add("cpf", pessoa.getCPF());
        return rp;
    }

}
