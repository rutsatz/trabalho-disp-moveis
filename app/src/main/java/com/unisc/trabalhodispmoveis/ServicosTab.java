package com.unisc.trabalhodispmoveis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.unisc.trabalhodispmoveis.util.AppConstants;
import com.unisc.trabalhodispmoveis.util.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class ServicosTab extends Activity {

    int userId;
    Context context;
    boolean emBusca;
    List<Map<String, Object>> listaPrestador, listaBusca;
    private ListView lstView;
    JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            // If the response is JSONObject instead of expected JSONArray

            try {
                JSONObject serverResp = new JSONObject(response.toString());
                boolean hasError = !serverResp.getBoolean("success");
                if (hasError) {

                    return;
                } else {
                    for (int x = 0; x < Integer.valueOf(serverResp.getString("total")); x++) {

                        Map<String, Object> mapa = new HashMap<>();
                        mapa.put("id_prestador", Integer.valueOf(serverResp.getJSONArray("prestador").getJSONObject(x).getString("id_login")));
                        mapa.put("nome", serverResp.getJSONArray("prestador").getJSONObject(x).getString("nome"));
                        mapa.put("dt_nasc", serverResp.getJSONArray("prestador").getJSONObject(x).getString("dt_nasc"));
                        mapa.put("telefone", serverResp.getJSONArray("prestador").getJSONObject(x).getString("telefone"));
                        mapa.put("cpf", serverResp.getJSONArray("prestador").getJSONObject(x).getString("cpf"));
                        mapa.put("tipo_servico", serverResp.getJSONArray("prestador").getJSONObject(x).getString("tipo_servico"));

                        listaPrestador.add(mapa);
                    }
                    preencheLista(listaPrestador);
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
    //ArrayList<Integer> listaID;
    private EditText buscaServico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicos_tab);

        userId = MainActivity.userId;
        context = this;
        emBusca = false;

        buscaServico = (EditText) findViewById(R.id.buscaServico);

        lstView = findViewById(R.id.lstServico);

        listaPrestador = new ArrayList<>();

        listaBusca = new ArrayList<>();

        HttpUtils.get(AppConstants.WS_LISTA_PRESTADOR, null, handler);

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Map<String, Object> mapa = listaPrestador.get(position);

                if (emBusca) {
                    mapa = listaBusca.get(position);
                }

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);

                intent.putExtra("id_prestador", (Integer) mapa.get("id_prestador"));
                intent.putExtra("nome", (String) mapa.get("nome"));
                intent.putExtra("dt_nasc", (String) mapa.get("dt_nasc"));
                intent.putExtra("telefone", (String) mapa.get("telefone"));
                intent.putExtra("cpf", (String) mapa.get("cpf"));
                intent.putExtra("tipo_servico", (String) mapa.get("tipo_servico"));

                intent.putExtra("layout", "servicos");

                startActivity(intent);
            }
        });

    }

    public void onClickBusca(View view) {
        emBusca = true;

        String busca = buscaServico.getText().toString();
        listaBusca.clear();

        if ("".equals(busca)) {
            emBusca = false;
            preencheLista(listaPrestador);
            return;
        } else {
            for (int i = 0; i < listaPrestador.size(); i++) {
                Map<String, Object> mapa = listaPrestador.get(i);
                String servico = (String) mapa.get("tipo_servico");

                if (servico.contains(busca)) {
                    listaBusca.add(listaPrestador.get(i));
                }
            }

            if (listaBusca.isEmpty()) {
                emBusca = false;
                Toast.makeText(this, "Serviço não encontrado.", Toast.LENGTH_SHORT).show();
                //MessageUtils.showAlert(context, "Serviço não encontrado.");
                preencheLista(listaPrestador);
                return;
            } else {
                preencheLista(listaBusca);
            }
        }


    }

    public void getListaContatos() {
        HttpUtils.get(AppConstants.WS_LISTA_PRESTADOR, null, handler);
    }

    public void preencheLista(List<Map<String, Object>> lista) {
        SimpleAdapter adapter = new MeuAdapter(context, lista, R.layout.uma_linha,
                new String[]{},
                new int[]{});

        lstView.setAdapter(adapter);
    }
}
