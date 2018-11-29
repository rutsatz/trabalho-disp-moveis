package com.unisc.trabalhodispmoveis;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.unisc.trabalhodispmoveis.service.LoginService;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

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

        // Testes
        String userId = "123456";
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("id_login", userId);
        startActivity(intent);
//        if (1 == 1) return;


    }


    public void onClickLogin(View view) {

        String email = String.valueOf(mEmailView.getText());
        String senha = String.valueOf(mPasswordView.getText());

        Log.d("teste", "email: " + email);
        Log.d("teste", "senha: " + senha);




        if ("".equals(email)) {
            Toast.makeText(this, "Preencha o campo de email.", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(senha)) {
            Toast.makeText(this, "Preencha o campo de senha.", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginService loginService = new LoginService();
        if (loginService.doLogin(email, senha)) {


        } else {
            Toast.makeText(this, "Usuario ou senha errado, tente novamente.", Toast.LENGTH_SHORT).show();
            return;
        }


//        String validacao = obj.getString("success");
//        if (validacao == "true") {
//            String userId = obj.getString("id_login");
//


    }


    public void onClickCadastro(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }


}

