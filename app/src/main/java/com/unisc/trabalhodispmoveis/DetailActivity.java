package com.unisc.trabalhodispmoveis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.unisc.trabalhodispmoveis.service.ServiceGPS;
import com.unisc.trabalhodispmoveis.util.AppConstants;
import com.unisc.trabalhodispmoveis.util.MessageUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DetailActivity extends AppCompatActivity {

    Context context;

    int id_prestador; // e  definido no quando o usuario clica no botao para contrata-lo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        context = this;

        Intent intent = getIntent();

        String layout = intent.getStringExtra("layout");

        TextView tvTitulo = findViewById(R.id.tituloDetail);

        TextView tvFone = findViewById(R.id.foneDetail);
        TextView tvDtNasc = findViewById(R.id.dtNascDetail);
        TextView tvNome = findViewById(R.id.nomeDetail);

        TextView tvCpf = findViewById(R.id.cpfDetail);
        TextView tvTipoServico = findViewById(R.id.tipoServicoDetail);

        LinearLayout linhaCpfDetail = findViewById(R.id.linhaCpfDetail);
        LinearLayout linhaServDetail = findViewById(R.id.linhaServDetail);
        LinearLayout infoContrato = findViewById(R.id.infoContratoDetail);

        tvFone.setText(intent.getStringExtra("telefone"));
        tvDtNasc.setText(intent.getStringExtra("dt_nasc"));
        tvNome.setText(intent.getStringExtra("nome"));

        if (layout.equals("clientes")) {
            tvTitulo.setText("Clientes");
        }

        if (layout.equals("contatos")) {
            tvTitulo.setText("Contatos");

            tvCpf.setText(intent.getStringExtra("cpf"));
            linhaCpfDetail.setVisibility(View.VISIBLE);

            tvTipoServico.setText(intent.getStringExtra("tipo_servico"));
            linhaServDetail.setVisibility(View.VISIBLE);
        }

        if (layout.equals("servicos")) {
            tvTitulo.setText("Serviços");

            infoContrato.setVisibility(View.VISIBLE);

            tvCpf.setText(intent.getStringExtra("cpf"));
            linhaCpfDetail.setVisibility(View.VISIBLE);

            tvTipoServico.setText(intent.getStringExtra("tipo_servico"));
            linhaServDetail.setVisibility(View.VISIBLE);

            id_prestador = intent.getIntExtra("id_prestador", 0);
            Log.d("teste", "id prestador : " + id_prestador);
        }

    }

    public void onClickContratar(View view) {

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
                        MessageUtils.showAlert(context, "Erro ao contratar serviço!");
                        return;
                    } else {
                        MessageUtils.showAlert(context, "Serviço contratado com sucesso!");
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

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.DATE_FORMAT);
        String dateString = sdf.format(date);

        ServiceGPS loc = new ServiceGPS(context);
        Log.d("teste", "Longitude" + loc.getLongitude());
        Log.d("teste", "Latitude" + loc.getLatitude());
        ArrayList<String> dados = new ArrayList<>();
        dados.add(String.valueOf(MainActivity.userId));
        dados.add(String.valueOf(id_prestador));
        dados.add(dateString);
        dados.add("Santa Cruz do Sul");

        if (loc.getLatitude() != null) {
            dados.add(loc.getLatitude());
        } else {
            dados.add("-29.6995877");
        }

        if (loc.getLongitude() != null) {
            dados.add(loc.getLatitude());
        } else {
            dados.add("-52.43861");
        }

        MessageUtils.showConfirm(this, "Tem certeza que deseja contratar este serviço.", dados, handler);
    }
}
