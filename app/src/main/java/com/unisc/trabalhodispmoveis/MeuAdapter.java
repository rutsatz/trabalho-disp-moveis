package com.unisc.trabalhodispmoveis;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

        Bitmap bmp = (Bitmap) mapa.get("img");
        String nome = (String) mapa.get("nome");
        String matr = (String) mapa.get("matr");

        TextView tv1 = v.findViewById(R.id.nomeList);
        tv1.setText(nome);

        TextView tv2 = v.findViewById(R.id.infoAList);
        tv2.setText(matr);

        TextView tv3 = v.findViewById(R.id.infoBList);
        tv2.setText(matr);

        ImageView img = v.findViewById(R.id.imgList);
        img.setImageBitmap(bmp);

        return v;
    }
}
