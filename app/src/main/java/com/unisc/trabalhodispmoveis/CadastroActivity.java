package com.unisc.trabalhodispmoveis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.unisc.trabalhodispmoveis.service.CadastroService;
import com.unisc.trabalhodispmoveis.util.MessageUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CadastroActivity extends AppCompatActivity {

    TextView novoUser, senha, senhaTeste;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        novoUser = (TextView) findViewById(R.id.novoUsuario);
        senha = (TextView) findViewById(R.id.novaSenha);
        senhaTeste = (TextView) findViewById(R.id.novaSenhaTeste);

        context = this;
    }

    public void novoCadastro(View view) {
        String strEmail = String.valueOf(novoUser.getText());
        String strSenha = String.valueOf(senha.getText());
        String strRepeteSenha = String.valueOf(senhaTeste.getText());

        if (!strSenha.equals(strRepeteSenha)) {
            senha.setText("");
            senhaTeste.setText("");
            MessageUtils.showAlert(context, "A senha nao foi digitada corretamente, tente novamente.");
            return;
        }


        JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("teste", "---------------- this is response : " + response);
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    Log.d("teste", "serverResp: " + serverResp);
                    boolean hasError = !serverResp.getBoolean("success");
                    Log.d("teste", "hasError: " + hasError);
                    if (hasError) {
                        MessageUtils.showAlert(context, "Usuário ou senha inválidos!");
                        return;
                    } else {

                        JSONObject login = serverResp.getJSONObject("login");

                        int userId = login.getInt("id_login");
                        Log.d("teste", "userId " + userId);
                        navigateMain(userId);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("teste", "statusCode: " + statusCode);
                Log.e("teste", "throwable: " + throwable);
                Log.e("teste", "1-Error: " + errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("teste", "statusCode: " + statusCode);
                Log.e("teste", "throwable: " + throwable);
                Log.e("teste", "2-Error: " + responseString);
                MessageUtils.showAlert(context,"Email já cadastrado.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("teste", "Error 2");
            }

        };


        CadastroService cadastroService = new CadastroService();
        cadastroService.salvarNovoUsuario(strEmail, strSenha, handler);


    }

    private void navigateMain(int userId) {
        Log.d("teste", "navigateMain");
        Intent intent = new Intent(context, MainActivity.class);
        Log.d("teste", "userId " + userId);
        intent.putExtra("id_login", userId);
        intent.putExtra("primeiroLogin", true);
        startActivity(intent);
    }

}
