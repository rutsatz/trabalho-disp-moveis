package com.unisc.trabalhodispmoveis;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);

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
    }
}
