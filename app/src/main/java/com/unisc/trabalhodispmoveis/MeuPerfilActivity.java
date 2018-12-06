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
    private Usuario usuario;

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
        usuario = MainActivity.usuario;

        if (usuario != null) {
            Log.d("testelista", "getTipoPessoa " + usuario.getTipoPessoa());
            Pessoa pessoa = usuario.getUsuarioPessoa();
            nome.setText(pessoa.getNome());
            dtNasc.setText(pessoa.getDataNascimentoAsString());
            email.setText(pessoa.getEmail());
            fone.setText(pessoa.getTelefone());
            if (usuario.getTipoPessoa() == TipoPessoa.PRESTADOR) {
                radioPrestador.setChecked(true);
            }
        }
        context = this;
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
                        MainActivity.showTabs();
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
