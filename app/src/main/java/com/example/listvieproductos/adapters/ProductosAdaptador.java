package com.example.listvieproductos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.listvieproductos.R;
import com.example.listvieproductos.models.Productos;

public class ProductosAdaptador extends ArrayAdapter<Productos>{
    Context context;
    ImageLoader queue;

    private class ViewHolder {
        TextView precio;
        TextView descuento;
        NetworkImageView image;

        private ViewHolder() {
        }
    }
    public ProductosAdaptador(Context context, List<Productos> items, ImageLoader _queue) {
        super(context, 0, items);
        this.context = context;
        this.queue = _queue;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        final Productos rowItem = (Productos) getItem(position);
        LayoutInflater mInflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.productos, null);
            holder = new ViewHolder();
            holder.image = (NetworkImageView)convertView.findViewById(R.id.image);
            holder.precio = (TextView) convertView.findViewById(R.id.precio);
            holder.descuento = (TextView) convertView.findViewById(R.id.descuento);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.precio.setText(rowItem.precio);
        holder.descuento.setText(rowItem.descuento);

     if ( rowItem.getSmallImage() != null ) {
            holder.image.setImageUrl(rowItem.getSmallImage(), queue);
        }
        return convertView;
    }
}