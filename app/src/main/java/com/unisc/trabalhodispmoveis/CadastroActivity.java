package com.unisc.trabalhodispmoveis;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
            return;
        }else{

            HttpTask task = new HttpTask();
            task.execute(String.valueOf(novoUser.getText()), String.valueOf(senha.getText()));
            try {
                String data = task.get();
                Log.d("WS", data);

                JSONObject obj = new JSONObject(data);
                String validacao = obj.getString("success");
                if(validacao == "true"){
                    String userId = obj.getString("id_login");

                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("id_login",userId);
                    startActivity(intent);
                }else{
                    Toast.makeText(this,"Erro no servidor.",Toast.LENGTH_SHORT).show();
                    return;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    class HttpTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://ghelfer-001-site8.itempurl.com/crialogin.php?");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                ContentValues values = new ContentValues();
                values.put("email", params[0]);
                values.put("senha", params[1]);
                InputStream inputStream = null;
                OutputStream out = con.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
                writer.write(getFormData(values));
                writer.flush();
                int statusCode = con.getResponseCode();
                if (statusCode == 200) {
                    inputStream = new BufferedInputStream(con.getInputStream());
                    if (inputStream != null)
                        return convertInputStreamToString(inputStream);
                    else
                        return "Erro nos dados  !";
                } else
                    return "Erro de conexeo!";
            } catch (Exception e) {
                return "Erro: " + e.getLocalizedMessage();

            }
        }

        private String getFormData(ContentValues values) throws UnsupportedEncodingException {
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, Object> entry : values.valueSet()) {
                if (first)
                    first = false;
                else
                    sb.append("&");

                sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
            }
            return sb.toString();
        }

        private String convertInputStreamToString(InputStream inputStream)
                throws IOException {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"), 8);
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }

    }
}
