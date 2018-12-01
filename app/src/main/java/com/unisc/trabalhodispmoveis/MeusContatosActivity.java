package com.unisc.trabalhodispmoveis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.unisc.trabalhodispmoveis.util.AppConstant;
import com.unisc.trabalhodispmoveis.util.HttpUtils;
import com.unisc.trabalhodispmoveis.util.MessageUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class MeusContatosActivity extends Activity {

    private ListView lstView;

    int userId = 7;
    Context context;

    List<Map<String,Object>> listaContatos;
    ArrayList<Integer> listaID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_contatos);

        lstView = findViewById(R.id.lstContatos);

        listaID = new ArrayList<Integer>();
        listaID.clear();

        listaContatos = new ArrayList<>();

        context = this;

        HttpUtils.get(AppConstant.WS_LISTA_CONTRATO,null,handler);

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Map<String, Object> mapa = listaContatos.get(position);

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);

                intent.putExtra("nome", (String) mapa.get("nome"));
                intent.putExtra("dt_nasc", (String) mapa.get("dt_nasc"));
                intent.putExtra("telefone", (String) mapa.get("telefone"));
                intent.putExtra("cpf", (String) mapa.get("cpf"));
                intent.putExtra("tipo_servico", (String) mapa.get("tipo_servico"));

                startActivity(intent);

            }
        });

    }

    JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            // If the response is JSONObject instead of expected JSONArray
            Log.d("teste2", "---------------- this is response : " + response);
            try {
                JSONObject serverResp = new JSONObject(response.toString());
                Log.d("teste", "serverResp: " + serverResp);
                boolean hasError = !serverResp.getBoolean("success");
                Log.d("teste", "hasError: " + hasError);
                if (hasError) {
                    MessageUtils.showAlert(context, "Usuário ou senha inválidos!");
                    return;
                } else {

                    if (listaID.size() == 0) {
                        Log.d("teste", "if");
                        //Filtra o id_cliente dos contratos pertencentes ao userId
                        for (int i = 0; i < Integer.valueOf(serverResp.getString("total")); i++) {
                            int id_cliente = Integer.valueOf(serverResp.getJSONArray("contrato").getJSONObject(i).getString("id_cliente"));
                            //Log.d("teste", "cliente: "+id_cliente);
                            if (userId == id_cliente) {
                                listaID.add(Integer.valueOf(serverResp.getJSONArray("contrato").getJSONObject(i).getString("id_prestador")));
                                //Log.d("teste", "add: "+Integer.valueOf(serverResp.getJSONArray("contrato").getJSONObject(i).getString("id_prestador")));
                            }
                        }

                        getListaContatos();

                    } else {
                        Log.d("teste", "else");
                        for (int x = 0; x < Integer.valueOf(serverResp.getString("total")); x++) {
                            int id_prestador = Integer.valueOf(serverResp.getJSONArray("prestador").getJSONObject(x).getString("id_prestador"));
                            Log.d("teste", "prestador: "+id_prestador);
                            for (int i = 0; i < listaID.size(); i++) {
                                if (id_prestador == listaID.get(i)) {
                                    Log.d("teste", "prestador: "+id_prestador +" = "+listaID.get(i));
                                    Map<String, Object> mapa = new HashMap<>();
                                    mapa.put("id_prestador", Integer.valueOf(serverResp.getJSONArray("prestador").getJSONObject(i).getString("id_prestador")));
                                    mapa.put("nome", serverResp.getJSONArray("prestador").getJSONObject(i).getString("nome"));
                                    mapa.put("dt_nasc", serverResp.getJSONArray("prestador").getJSONObject(i).getString("dt_nasc"));
                                    mapa.put("telefone", serverResp.getJSONArray("prestador").getJSONObject(i).getString("telefone"));
                                    mapa.put("cpf", serverResp.getJSONArray("prestador").getJSONObject(i).getString("cpf"));
                                    mapa.put("tipo_servico", serverResp.getJSONArray("prestador").getJSONObject(i).getString("tipo_servico"));

                                    listaContatos.add(mapa);
                                }
                            }
                        }
                        preencheLista(listaContatos);
                    }

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

    public void getListaContatos(){
        HttpUtils.get(AppConstant.WS_LISTA_PRESTADOR, null, handler);
    }

    public void preencheLista(List<Map<String,Object>> lista){
        SimpleAdapter adapter = new MeuAdapter(context, lista, R.layout.uma_linha,
                new String[]{"nome", "telefone"},
                new int[]{R.id.nomeList, R.id.foneList});

        lstView.setAdapter(adapter);
    }
}
