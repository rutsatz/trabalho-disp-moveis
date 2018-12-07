package com.unisc.trabalhodispmoveis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.unisc.trabalhodispmoveis.model.Pessoa;
import com.unisc.trabalhodispmoveis.model.TipoPessoa;
import com.unisc.trabalhodispmoveis.model.Usuario;
import com.unisc.trabalhodispmoveis.service.LoginService;
import com.unisc.trabalhodispmoveis.service.PessoaService;
import com.unisc.trabalhodispmoveis.util.AppConstants;
import com.unisc.trabalhodispmoveis.util.MessageUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static int userId;
    private static String email;
    private static String senha;
    Context context;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private JsonHttpResponseHandler handlerListaCliente;
    private JsonHttpResponseHandler handlerListaPrestador;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mEmailView.setText("teste1@teste1.com");
        mPasswordView.setText("123456");

        sharedPreferences = getSharedPreferences(getString(R.string.pref_key), Context.MODE_PRIVATE);

        // Testes
//        String userId = "123456";
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("id_login", userId);
//        startActivity(intent);
//        if (1 == 1) return;

        // Lista Prestador
        handlerListaPrestador = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("teste", "---------------- this is response : " + response);
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    Log.d("teste", "###### handlerListaPrestador #######");
                    Log.d("teste", "serverResp: " + serverResp);
                    boolean hasError = !serverResp.getBoolean("success");
                    Log.d("teste", "hasError: " + hasError);
                    if (hasError) {
                        // Se prestador tbm nao existe, entao ocorreu um erro e nao achou os dados.
                        MessageUtils.showAlert(context, "Dados não encontrados!");
                        return;
                    } else {
                        // Se achou prestador, monta objeto.
                        try {
                            Usuario u = buildUsuario(userId, email, serverResp, TipoPessoa.PRESTADOR);
                            navigateMain(u);
                        } catch (JSONException e) {
                            MessageUtils.showAlert(context, "Erro ao processar dados do server!");
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


        // Lista Cliente
        handlerListaCliente = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    Log.d("teste", "handlerListaCliente serverResp: " + serverResp);
                    boolean hasError = !serverResp.getBoolean("success");
                    Log.d("teste", "hasError: " + hasError);

                    if (hasError) {
                        MessageUtils.showAlert(context, "Dados nao encontrados!");
                        return;
                    } else {
                        try {
                            Usuario u = buildUsuario(userId, email, serverResp, TipoPessoa.CLIENTE);
                            navigateMain(u);
                        } catch (JSONException e) {
//                            MessageUtils.showAlert(context, "Erro ao processar dados do servidor.");
                            // Se nao encontrou cliente, busca o prestador.
                            Log.i("teste", "Cliente nao encontrado. Buscando prestador.");
                            listaPrestador(userId, handlerListaPrestador);
                        }
                    }

                } catch (JSONException e) {
                    // Se nao encontrou cliente, busca o prestador.
                    Log.i("teste", "Cliente nao encontrado. Buscando prestador.");
                    listaPrestador(userId, handlerListaPrestador);
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


        context = this;

        trataAutoLogin();
    }

    private void trataAutoLogin() {
        int loginCount = sharedPreferences.getInt(getString(R.string.pref_login_count), -1);
        Log.i("teste", "loginCount: " + loginCount);
        if (loginCount != -1) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            boolean autoLogin = false;
            if (loginCount == AppConstants.AUTO_LOGIN_LIMIT) {
                Log.i("login", "Atingiu limite de auto login");
                editor.remove(getString(R.string.pref_login_count));
                editor.remove(getString(R.string.pref_user_email));
                editor.remove(getString(R.string.pref_user_id));
                editor.apply();
            } else {
                Log.i("login", "efetuando auto login");
                autoLogin = true;
                editor.putInt(getString(R.string.pref_login_count), ++loginCount);
                editor.apply();
            }
            if (autoLogin) entrarDireto();
        }
    }

    public void entrarDireto() {
        Log.i("login", "entrarDireto");
        LoginActivity.email = sharedPreferences.getString(getString(R.string.pref_user_email), "");
        userId = sharedPreferences.getInt(getString(R.string.pref_user_id), -1);

        listaCliente(userId, handlerListaCliente);
    }

    public void saveFirstLoginPreferences(int userId, String email) {
        Log.i("login", "saveFirstLoginPreferences");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.pref_login_count), 1);
        editor.putString(getString(R.string.pref_user_email), email);
        editor.putInt(getString(R.string.pref_user_id), userId);
        editor.apply();
    }

    public void onClickLogin(View view) {

        email = String.valueOf(mEmailView.getText());
        senha = String.valueOf(mPasswordView.getText());

        Log.d("teste", "email: " + email);
        Log.d("teste", "senha: " + senha);


        if ("".equals(email)) {
            MessageUtils.showAlert(context, "Preencha o campo de email.");
            return;
        }
        if ("".equals(senha)) {
            MessageUtils.showAlert(context, "Preencha o campo de senha.");
            return;
        }

        // Valida Login
        JsonHttpResponseHandler handlerLogin = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    Log.d("teste", "handlerLogin serverResp: " + serverResp);
                    boolean hasError = !serverResp.getBoolean("success");
                    Log.d("teste", "hasError: " + hasError);
                    if (hasError) {
                        MessageUtils.showAlert(context, "Usuário ou senha inválidos!");
                        return;
                    } else {
                        // Se usuario existe, busca dados do cliente.
                        userId = serverResp.getInt("id_login");
                        LoginActivity.email = serverResp.getString("email");

                        saveFirstLoginPreferences(userId, LoginActivity.email);
                        listaCliente(userId, handlerListaCliente);
                    }
                } catch (JSONException e) {
                    MessageUtils.showAlert(context, "Erro ao fazer login. Erro ao processar retorno do server.");
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

        LoginService loginService = new LoginService();
        // Async
        loginService.doLogin(email, senha, handlerLogin);
    }

    private Usuario buildUsuario(int userId, String email, JSONObject serverResp, TipoPessoa tipoPessoa) throws JSONException {

        Usuario usuario = new Usuario();

        List<Pessoa> pessoas = new ArrayList<>();

        Log.d("teste", "tipoPessoa " + tipoPessoa);
        Log.d("buildp", "buildPessoa " + serverResp);

        String field;
        JSONArray jsonArray;
        if (tipoPessoa == TipoPessoa.CLIENTE) {
            field = "cliente";
        } else {
            field = "prestador";
        }

        jsonArray = serverResp.getJSONArray(field);
        Log.d("buildp", "jsonArray " + jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            Pessoa pessoa = new Pessoa();
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            pessoa.setUserId(jsonObject.getInt("id_login"));
            pessoa.setNome(jsonObject.getString("nome"));
            pessoa.setEmail(email);
            pessoa.setDataNascimento(jsonObject.getString("dt_nasc"));
            pessoa.setTelefone(jsonObject.getString("telefone"));

            pessoas.add(pessoa);

        }
        usuario.setPessoas(pessoas);
        usuario.setTipoPessoa(tipoPessoa);

//        Pessoa pessoaUsuario = new Pessoa();
//        pessoaUsuario.setUserId(userId);
//        pessoaUsuario.setEmail(email);
//        usuario.setUsuarioPessoa(pessoaUsuario);


        usuario.setUsuarioPessoa(pessoas.get(0));

        return usuario;
    }

    private void listaCliente(int userId, JsonHttpResponseHandler handler) {
        PessoaService pessoaService = new PessoaService();
        pessoaService.listaCliente(userId, handler);
    }

    private void listaPrestador(int userId, JsonHttpResponseHandler handler) {
        PessoaService pessoaService = new PessoaService();
        pessoaService.listaPrestador(userId, handler);
    }


    private void navigateMain(Usuario usuario) {
        Intent intent = new Intent(context, MainActivity.class);

        intent.putExtra("usuario", usuario);
        intent.putExtra("primeiroLogin", false);
        startActivity(intent);
    }

    public void onClickCadastro(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }


}

