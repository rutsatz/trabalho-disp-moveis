package com.unisc.trabalhodispmoveis.service;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.unisc.trabalhodispmoveis.util.AppConstants;
import com.unisc.trabalhodispmoveis.util.HttpUtils;

public class ContratoService {
    public void salvarNovoContrato(String id_cliente, String id_prestador, String dt_inicio, String local, String latitude, String longitude, JsonHttpResponseHandler handler) {
        RequestParams rp = new RequestParams();
        rp.add("id_cliente", id_cliente);
        rp.add("id_prestador", id_prestador);
        rp.add("dt_inicio", dt_inicio);
        rp.add("local", local);
        rp.add("latitude", latitude);
        rp.add("longitude", longitude);
        HttpUtils.post(AppConstants.WS_CRIA_CONTRATO, rp, handler);
    }
}
