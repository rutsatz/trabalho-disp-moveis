package com.unisc.trabalhodispmoveis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {

    TextView novoUser, senha, senhaTeste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        novoUser = (TextView) findViewById(R.id.novoUsuario);
        senha = (TextView) findViewById(R.id.novaSenha);
        senhaTeste = (TextView) findViewById(R.id.novaSenhaTeste);

    }

    public void novoCadastro(View view) {
        if (senha != senhaTeste){
            Toast.makeText(this,"A senha nao foi digitada corretamente, tente novamente.", Toast.LENGTH_SHORT).show();
            senha.setText("");
            senhaTeste.setText("");
        }else{


        }



    }
}
