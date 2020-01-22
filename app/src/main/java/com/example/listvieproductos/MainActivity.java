package com.example.listvieproductos;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.example.listvieproductos.adapters.ProductosAdaptador;
import com.example.listvieproductos.helpers.QueueUtils;
import com.example.listvieproductos.models.Productos;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView ProductosList;
    ProductosAdaptador productosAdaptador;
    QueueUtils.QueueObject queue = null;
    ArrayList<Productos> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProductosList = findViewById(R.id.productosList);
        queue = QueueUtils.getInstance(this.getApplicationContext());
        items = new ArrayList<>();
        Productos.injectProductosFromCloud(queue, items, this);

        productosAdaptador = new ProductosAdaptador(this, items, queue.getImageLoader());
        ProductosList.setAdapter(productosAdaptador);

        Productos.sendRequestPOST(queue, this);
    }

    public void refreshList(){
        if ( productosAdaptador!= null ) {
            productosAdaptador.notifyDataSetChanged();
        }
    }
}
