package com.unisc.trabalhodispmoveis;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

class MeuAdapter extends SimpleAdapter {

    List<Map<String, Object>> lista;



    public MeuAdapter(Context ctx, List<Map<String, Object>> lista, int uma_linha, String[] strings, int[] ints) {
        super(ctx, lista, uma_linha,strings,ints);
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);

        Map<String, Object> mapa = lista.get(position);

        String nome = (String) mapa.get("nome");
        String fone = (String) mapa.get("telefone");

        String tipoServico = (String) mapa.get("tipo_servico");

        TextView tv1 = v.findViewById(R.id.nomeList);
        tv1.setText(nome);

        TextView tv2 = v.findViewById(R.id.foneList);
        tv2.setText(fone);

        TextView tv3 = v.findViewById(R.id.infoAlList);
        tv3.setText(tipoServico);

        if(v.getContext() instanceof ServicosTab){
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.VISIBLE);
            tv1.setTextSize(15);
            tv3.setTextSize(20);
        }

        return v;
    }
}
