package com.unisc.trabalhodispmoveis.service;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.unisc.trabalhodispmoveis.util.AppConstants;
import com.unisc.trabalhodispmoveis.util.HttpUtils;

public class PessoaService {

    public void listaCliente(int userId, JsonHttpResponseHandler handler) {
        RequestParams rp = new RequestParams();
        rp.add("id_login", String.valueOf(userId));
        HttpUtils.post(AppConstants.WS_LISTA_CLIENTE, rp, handler);
    }

    public void listaPrestador(int userId, JsonHttpResponseHandler handler) {
        RequestParams rp = new RequestParams();
        rp.add("id_login", String.valueOf(userId));
        HttpUtils.post(AppConstants.WS_LISTA_PRESTADOR, rp, handler);
    }

}
