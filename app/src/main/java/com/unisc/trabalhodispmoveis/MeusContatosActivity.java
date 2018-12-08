package com.unisc.trabalhodispmoveis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.unisc.trabalhodispmoveis.util.AppConstants;
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

    int userId;
    Context context;
    List<Map<String, Object>> listaContatos;
    ArrayList<Integer> listaID;
    private ListView lstView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_contatos);

        userId = MainActivity.userId;
        context = this;

        lstView = findViewById(R.id.lstContatos);

        listaID = new ArrayList<Integer>();
        listaID.clear();

        listaContatos = new ArrayList<>();

        //atualizaLista();

        //HttpUtils.get(AppConstants.WS_LISTA_CONTRATO,null,handler);

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

                intent.putExtra("layout", "contatos");

                startActivity(intent);

            }
        });

    }

    public void atualizaLista() {

        final JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
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

        HttpUtils.get(AppConstants.WS_LISTA_CONTRATO, null, handler);
    }

    public void getListaContatos() {


        final JsonHttpResponseHandler handlerListaPrestador = new JsonHttpResponseHandler() {
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
                        JSONArray prestador = serverResp.getJSONArray("prestador");

                        //for (int x = 0; x < Integer.valueOf(serverResp.getString("total")); x++) {
                        //    int id_prestador = 0;
                        //    id_prestador = Integer.valueOf(prestador.getJSONObject(x).getString("id_prestador"));

                        //Log.d("teste", "prestador: " + id_prestador);
                        for (int i = 0; i < listaID.size(); i++) {

                            for (int x = 0; x < Integer.valueOf(serverResp.getString("total")); x++) {
                                int id_prestador = 0;
                                id_prestador = Integer.valueOf(prestador.getJSONObject(x).getString("id_login"));


                                if (id_prestador == listaID.get(i)) {

                                    Log.d("teste", "prestador: " + id_prestador + " = " + listaID.get(i));
                                    Map<String, Object> mapa = new HashMap<>();
                                    int id = Integer.valueOf(prestador.getJSONObject(x).getString("id_prestador"));
                                    mapa.put("id_prestador", id);
                                    String nome = prestador.getJSONObject(x).getString("nome");
                                    mapa.put("nome", nome);
                                    mapa.put("dt_nasc", prestador.getJSONObject(x).getString("dt_nasc"));
                                    mapa.put("telefone", prestador.getJSONObject(x).getString("telefone"));
                                    mapa.put("cpf", prestador.getJSONObject(x).getString("cpf"));
                                    mapa.put("tipo_servico", prestador.getJSONObject(x).getString("tipo_servico"));

                                    listaContatos.add(mapa);

                                }


                            }
                        }

                        preencheLista(listaContatos);
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


        };
        HttpUtils.get(AppConstants.WS_LISTA_PRESTADOR, null, handlerListaPrestador);


    }

    public void preencheLista(List<Map<String, Object>> lista) {
        SimpleAdapter adapter = new MeuAdapter(context, lista, R.layout.uma_linha,
                new String[]{"nome", "telefone"},
                new int[]{R.id.nomeList, R.id.foneList});

        lstView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaContatos.clear();
        listaID.clear();
        atualizaLista();
        Log.d("resume", "on resume");
    }
}
