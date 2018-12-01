package com.unisc.trabalhodispmoveis.service;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.unisc.trabalhodispmoveis.util.AppConstants;
import com.unisc.trabalhodispmoveis.util.HttpUtils;

public class LoginService {

    public void doLogin(String email, String senha, JsonHttpResponseHandler handler) {
        RequestParams rp = new RequestParams();
        rp.add("email", email);
        rp.add("senha", senha);
        HttpUtils.post(AppConstants.WS_VALIDA_LOGIN, rp, handler);
    }

}
