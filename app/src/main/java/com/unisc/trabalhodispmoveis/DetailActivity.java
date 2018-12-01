package com.unisc.trabalhodispmoveis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        TextView tvFone = findViewById(R.id.foneDetail);
        TextView tvDtNasc = findViewById(R.id.dtNascDetail);
        TextView tvNome = findViewById(R.id.nomeDetail);

        TextView tvCpf = findViewById(R.id.cpfDetail);
        TextView tvTipoServico = findViewById(R.id.tipoServicoDetail);

        tvFone.setText("Telefone: " + intent.getStringExtra("telefone"));
        tvDtNasc.setText("Data de Nascimento: " + intent.getStringExtra("dt_nasc"));
        tvNome.setText("Nome: " + intent.getStringExtra("nome"));

        if(intent.getStringExtra("tipo_servico") != null){
            tvCpf.setText("CPF: " + intent.getStringExtra("cpf"));
            tvCpf.setVisibility(View.VISIBLE);
            tvTipoServico.setText("Tipo de servico: " + intent.getStringExtra("tipo_servico"));
            tvTipoServico.setVisibility(View.VISIBLE);
        }

    }
}
