package com.unisc.trabalhodispmoveis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unisc.trabalhodispmoveis.util.MessageUtils;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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

        if(layout.equals("clientes")){
            tvTitulo.setText("Clientes");
        }

        if(layout.equals("contatos")){
            tvTitulo.setText("Contatos");

            tvCpf.setText(intent.getStringExtra("cpf"));
            linhaCpfDetail.setVisibility(View.VISIBLE);

            tvTipoServico.setText(intent.getStringExtra("tipo_servico"));
            linhaServDetail.setVisibility(View.VISIBLE);
        }

        if(layout.equals("servicos")){
            tvTitulo.setText("Serviços");

            infoContrato.setVisibility(View.VISIBLE);

            tvCpf.setText(intent.getStringExtra("cpf"));
            linhaCpfDetail.setVisibility(View.VISIBLE);

            tvTipoServico.setText(intent.getStringExtra("tipo_servico"));
            linhaServDetail.setVisibility(View.VISIBLE);
        }

    }

    public void onClickContratar(View view) {

        MessageUtils.showConfirm(this, "Tem certeza que deseja contratar este serviço.");


    }


}
