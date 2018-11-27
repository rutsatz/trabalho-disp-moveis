package com.unisc.trabalhodispmoveis;

import android.app.Activity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

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

public class MeuPerfilActivity extends Activity {

    private TextView tipoServ, userIDtv;
    private EditText tipoServico, nome, dtNasc, email, fone, endereco, cpf;
    private String tipoUsuario;
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

        userIDtv = findViewById(R.id.userIdperfil);

        userId = MainActivity.getUserId();

        userIDtv.setText(Integer.toString(userId));

    }

    public void perfilCancelar(View view) {
    }

    public void perfilSalvar(View view) {

        HttpTask task = new HttpTask();

        if(tipoUsuario == "Cliente"){
            task.execute(String.valueOf(nome.getText()), String.valueOf(userId), String.valueOf(dtNasc.getText()), String.valueOf(fone.getText()));
        }else{
            task.execute(String.valueOf(nome.getText()), String.valueOf(userId), String.valueOf(dtNasc.getText()), String.valueOf(fone.getText()), String.valueOf(cpf.getText()), String.valueOf(tipoServico.getText()));
        }

        task.execute(String.valueOf(nome.getText()), String.valueOf(userId), "lab-password");
        try {
            String data = task.get();
            Log.d("WS", data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    class HttpTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://ghelfer-001-site8.itempurl.com/criaCliente.php?");

                ContentValues values = new ContentValues();
                values.put("nome", params[0]);
                values.put("id_login", params[1]);
                values.put("dt_nasc", params[2]);
                values.put("telefone", params[3]);

                if(tipoUsuario == "Prestador"){
                    url = new URL("http://ghelfer-001-site8.itempurl.com/criaPrestador.php?");

                    values.put("nome", params[0]);
                    values.put("id_login", params[1]);
                    values.put("dt_nasc", params[2]);
                    values.put("telefone", params[3]);
                    values.put("cpf", params[4]);
                    values.put("tipo_servico", params[4]);
                }

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");

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


    public void onRadioButtonClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
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
