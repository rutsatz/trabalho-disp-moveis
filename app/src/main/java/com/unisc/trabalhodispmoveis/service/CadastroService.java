package com.unisc.trabalhodispmoveis.service;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.unisc.trabalhodispmoveis.util.AppConstant;
import com.unisc.trabalhodispmoveis.util.HttpUtils;

public class CadastroService {
    public boolean salvarNovoUsuario(String email, String senha, JsonHttpResponseHandler handler) {

        RequestParams rp = new RequestParams();
        rp.add("email", email);
        rp.add("senha", senha);

        HttpUtils.post(AppConstant.WS_CRIA_LOGIN, rp, handler);

        return false;
    }
}
