package com.unisc.trabalhodispmoveis;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.unisc.trabalhodispmoveis.service.LoginService;
import com.unisc.trabalhodispmoveis.util.MessageUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    Context context;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mEmailView.setText("teste1@teste1.com");
        mPasswordView.setText("123456");

        // Testes
        String userId = "123456";
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("id_login", userId);
        startActivity(intent);
//        if (1 == 1) return;

        context = this;
    }


    public void onClickLogin(View view) {

        String email = String.valueOf(mEmailView.getText());
        String senha = String.valueOf(mPasswordView.getText());

        Log.d("teste", "email: " + email);
        Log.d("teste", "senha: " + senha);


        if ("".equals(email)) {
            MessageUtils.showAlert(context, "Preencha o campo de email.");
            return;
        }
        if ("".equals(senha)) {
            MessageUtils.showAlert(context, "Preencha o campo de senha.");
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
                        int userId = serverResp.getInt("id_login");
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
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("teste", "Error 2");
            }

        };


        LoginService loginService = new LoginService();
        // Async
        loginService.doLogin(email, senha, handler);


//        String validacao = obj.getString("success");
//        if (validacao == "true") {
//            String userId = obj.getString("id_login");
//


    }


    private void navigateMain(int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("id_login", userId);
        intent.putExtra("primeiroLogin", false);
        startActivity(intent);
    }

    public void onClickCadastro(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }


}

