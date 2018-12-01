package com.unisc.trabalhodispmoveis;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.unisc.trabalhodispmoveis.model.Pessoa;
import com.unisc.trabalhodispmoveis.model.TipoPessoa;
import com.unisc.trabalhodispmoveis.model.Usuario;
import com.unisc.trabalhodispmoveis.service.MeuPerfilService;
import com.unisc.trabalhodispmoveis.util.MessageUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class MeuPerfilActivity extends Activity {

    Context context;
    private TextView tipoServ, userIDtv;
    private EditText tipoServico, nome, dtNasc, email, fone, endereco, cpf;
    private String tipoUsuario;
    private RadioButton radioCliente, radioPrestador;
    private RadioGroup radioTipoCliente;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_perfil);

        tipoServ = (TextView) findViewById(R.id.perfilTipoServTextView);
        tipoServico = (EditText) findViewById(R.id.perfilTipoServ);
        nome = (EditText) findViewById(R.id.perfilNome);
        dtNasc = (EditText) findViewById(R.id.perfilDataNasc);
        email = (EditText) findViewById(R.id.perfilEmail);
        fone = (EditText) findViewById(R.id.perfilTel);
        endereco = (EditText) findViewById(R.id.perfilEndereco);
        cpf = (EditText) findViewById(R.id.perfilCPF);

        radioCliente = (RadioButton) findViewById(R.id.radioCliente);
        radioCliente.setChecked(true);

        radioPrestador = (RadioButton) findViewById(R.id.radioPrestador);
        radioTipoCliente = (RadioGroup) findViewById(R.id.radioTipoCliente);

        userIDtv = findViewById(R.id.userIdperfil);

        userId = MainActivity.userId;

        userIDtv.setText(Integer.toString(userId));
        context = this;

        Log.d("testelista", "onCreate");
        Log.d("testelista", "userId " + userId);
//        Log.d("testelista", "getTipoPessoa " + MainActivity.usuario.getTipoPessoa());
//        Log.d("testelista", "Usuarios " + MainActivity.usuario.getPessoas());

    }

    public void perfilCancelar(View view) {
    }

    public void perfilSalvar(View view) {

        String strNome = nome.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String strDataNascimento = dtNasc.getText().toString();
        Date dataNascimento;
        try {
            dataNascimento = sdf.parse(strDataNascimento);
        } catch (ParseException e) {
            MessageUtils.showAlert(context, "Data de nascimento inválida!");
            return;
        }
        String strEmail = email.getText().toString();
        String strFone = fone.getText().toString();
        String strEndereco = endereco.getText().toString();
        String strCPF = cpf.getText().toString();

        boolean isCliente = radioCliente.isChecked();

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
                        MessageUtils.showAlert(context, "Erro ao salvar dados!");
                        return;
                    } else {
                        MessageUtils.showToast(context, "Dados salvos com sucesso!");
                        showTabs();
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
                Log.d("teste", "onSuccess JSONArray " + timeline);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("teste", "statusCode: " + statusCode);
                Log.e("teste", "throwable: " + throwable);
                Log.e("teste", "2-Error: " + responseString);
                MessageUtils.showAlert(context, "Erro ao salvar dados.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("teste", "Error 2");
            }

        };


        MeuPerfilService meuPerfilService = new MeuPerfilService();

        if (isCliente) {
            Pessoa pessoa = new Pessoa(userId, strNome, strEmail, dataNascimento, strFone,
                    strEndereco, strCPF);
            Usuario usuario = new Usuario(TipoPessoa.CLIENTE, pessoa);
            meuPerfilService.salvarCliente(usuario, handler);
        } else {
            String strTipoServico = tipoServico.getText().toString();
            Pessoa pessoa = new Pessoa(userId, strNome, strEmail, dataNascimento, strFone,
                    strEndereco, strCPF, strTipoServico);
            Usuario usuario = new Usuario(TipoPessoa.PRESTADOR, pessoa);
            meuPerfilService.salvarPrestador(usuario, handler);
        }
    }

    private void showTabs() {
        MainActivity.tabHost.getTabWidget().getChildAt(1).setVisibility(View.VISIBLE); // Meus clientes
        MainActivity.tabHost.getTabWidget().getChildAt(2).setVisibility(View.VISIBLE); // Meus contratos
        MainActivity.tabHost.getTabWidget().getChildAt(3).setVisibility(View.VISIBLE); // Servicos
        MainActivity.tabHost.setCurrentTab(1); // Meus clientes
    }

    public void onRadioButtonClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioCliente:
                if (checked)
                    tipoServ.setVisibility(View.INVISIBLE);
                tipoServico.setVisibility(View.INVISIBLE);
                tipoUsuario = "Cliente";
                break;
            case R.id.radioPrestador:
                if (checked)
                    tipoServ.setVisibility(View.VISIBLE);
                tipoServico.setVisibility(View.VISIBLE);
                tipoUsuario = "Prestador";
                break;
        }

    }


}
