package com.unisc.trabalhodispmoveis;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;

import com.unisc.trabalhodispmoveis.model.TipoPessoa;
import com.unisc.trabalhodispmoveis.model.Usuario;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

    public static TabHost tabHost;
    public static int userId;
    public static boolean primeiroLogin;
    public static Usuario usuario;
    Context context;

    public static void showTabs() {

        Log.d("pessoa", "showTabs tipoPessoa " + usuario.getTipoPessoa().toString());

        // Para prestador, exibe os Clientes.
        if (usuario.getTipoPessoa() == TipoPessoa.PRESTADOR) {
            tabHost.getTabWidget().getChildAt(1).setVisibility(View.VISIBLE); // Meus clientes
            tabHost.getTabWidget().getChildAt(2).setVisibility(View.GONE); // Meus contratos
            tabHost.getTabWidget().getChildAt(3).setVisibility(View.GONE); // Servicos
        } else {
            // Para Cliente, exibe Meus Contatos e Servicos
            tabHost.getTabWidget().getChildAt(1).setVisibility(View.GONE); // Meus clientes
            tabHost.getTabWidget().getChildAt(2).setVisibility(View.VISIBLE); // Meus contratos
            tabHost.getTabWidget().getChildAt(3).setVisibility(View.VISIBLE); // Servicos
        }
        tabHost.setCurrentTab(1); // Meus clientes
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        usuario = (Usuario) intent.getSerializableExtra("usuario");
        Log.d("teste", "%%%% usuario %%%" + usuario);

        if (usuario != null)
            userId = usuario.getUsuarioPessoa().getUserId();
        primeiroLogin = intent.getBooleanExtra("primeiroLogin", false);

        Log.d("teste", "MainActivity userId " + userId);
        Log.d("teste", "MainActivity primeiroLogin " + primeiroLogin);

        tabHost = (TabHost) findViewById(android.R.id.tabhost);

        TabHost.TabSpec tabPerfil = tabHost.newTabSpec("abaPerfil");
        TabHost.TabSpec tabCliente = tabHost.newTabSpec("abaCliente");
        TabHost.TabSpec tabContato = tabHost.newTabSpec("abaContato");
        TabHost.TabSpec tabServico = tabHost.newTabSpec("abaServico");

        tabPerfil.setIndicator("Meu Perfil");
        tabCliente.setIndicator("Meus Clientes");
        tabContato.setIndicator("Meus Contatos");
        tabServico.setIndicator("Serviços");

        tabPerfil.setContent(new Intent(this, MeuPerfilActivity.class));
        tabCliente.setContent(new Intent(this, MeusClientesActivity.class));
        tabContato.setContent(new Intent(this, MeusContatosActivity.class));
        tabServico.setContent(new Intent(this, servicosTab.class));

        tabHost.addTab(tabPerfil);
        tabHost.addTab(tabCliente);
        tabHost.addTab(tabContato);
        tabHost.addTab(tabServico);

        if (primeiroLogin) {
            tabHost.getTabWidget().getChildAt(1).setVisibility(View.GONE);
            tabHost.getTabWidget().getChildAt(2).setVisibility(View.GONE);
            tabHost.getTabWidget().getChildAt(3).setVisibility(View.GONE);
        } else {
            showTabs();
        }
        context = this;
    }
}
