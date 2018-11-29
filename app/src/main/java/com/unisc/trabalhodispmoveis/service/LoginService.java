package com.unisc.trabalhodispmoveis.service;

import android.preference.PreferenceActivity;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.unisc.trabalhodispmoveis.util.AppConstant;
import com.unisc.trabalhodispmoveis.util.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginService {

    public boolean doLogin(String email, String senha) {

        RequestParams rp = new RequestParams();
        rp.add("email", email);
        rp.add("senha", senha);

        HttpUtils.post(AppConstant.WS_ATUALIZA_CLIENTE, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline

            }
        });

        return false;
    }

}
